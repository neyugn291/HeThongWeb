import React, { useEffect, useState } from "react";
import Apis, { endpoints, authApis } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";
import { Card, Container, Alert, Button } from "react-bootstrap";
import cookies from 'react-cookies'

const MyBooking = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const fetchBookings = async () => {
    try {
      let res = await authApis().get(endpoints['mybookings']);
      setBookings(res.data);
    } catch (err) {
      console.error(err);
      setError("Không thể tải đơn đặt chỗ.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBookings();
  }, []);

  const handleDeleteBooking = async (bookingId) => {
    if (!window.confirm("Bạn có chắc muốn huỷ đặt chỗ này không?")) return;

    try {
      await authApis().delete(`${endpoints['delete-booking']}/${bookingId}`);
      alert("Huỷ đặt chỗ thành công!");
      await fetchBookings();
    } catch (err) {
      console.error(err);
      alert("Lỗi khi huỷ đặt chỗ.");
    }
  };


  const handleDirectPayment = async (bookingId) => {
    try {
      let res = await authApis().post(`${endpoints['payBooking']}/${bookingId}`);
      alert("Thanh toán thành công!");
      await fetchBookings();
    } catch (err) {
      console.error(err);
      alert("Lỗi khi thanh toán.");
    }
  };

  const downloadInvoice = async (bookingId) => {
    const res = await fetch(`http://localhost:8080/ParkingWeb/api/booking/pdf/${bookingId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${cookies.load("token")}`,
      },
    });

    const blob = await res.blob();
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `invoice-${bookingId}.pdf`;
    a.click();
    window.URL.revokeObjectURL(url);
  };


  if (loading) return <MySpinner />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Container className="mt-5">
      <h2 className="mb-4">Đơn đặt chỗ của bạn</h2>
      {bookings.length === 0 ? (
        <Alert variant="info">Bạn chưa có đơn đặt chỗ nào.</Alert>
      ) : (
        bookings.map((b) => (
          <Card key={b.bookingId} className="mb-4 shadow-sm">
            <Card.Body>
              <Card.Title className="mb-3">
                Đơn #{b.bookingId}
              </Card.Title>
              <Card.Text><strong>Biển số:</strong> {b.licensePlateId.licensePlate}</Card.Text>
              <Card.Text><strong>Thời gian:</strong> {new Date(b.startTime).toLocaleString()} - {new Date(b.endTime).toLocaleString()}</Card.Text>
              <Card.Text><strong>Trạng thái:</strong> {b.status}</Card.Text>

              {b.invoiceId ? (
                <>
                  <Card.Text><strong>Tổng tiền:</strong> {b.invoiceId.totalAmount} VND</Card.Text>
                  <Card.Text><strong>Trạng thái hóa đơn:</strong> {b.invoiceId.status}</Card.Text>
                  {b.invoiceId.status !== "PAID" && b.status === "ACTIVE" &&(
                    <>
                      <Button
                        variant="success"
                        size="sm"
                        className="me-2"
                        onClick={() => handleDirectPayment(b.bookingId)}>
                        Thanh toán
                      </Button>
                      <Button
                        variant="danger"
                        size="sm"
                        onClick={() => handleDeleteBooking(b.bookingId)}>
                        Huỷ đặt chỗ
                      </Button>
                    </>)}
                  {b.invoiceId.status === "PAID" && (
                    <Button onClick={() => downloadInvoice(b.bookingId)}>Tải hóa đơn (PDF)</Button>)}
                </>
              ) : (
                <Card.Text className="text-muted">Chưa có hóa đơn</Card.Text>
              )}
            </Card.Body>
          </Card>
        ))
      )}
    </Container>
  );
};

export default MyBooking;
