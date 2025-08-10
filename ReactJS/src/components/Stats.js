import React, { useEffect, useState } from "react";
import { Card, Table, Alert, Spinner, Form, Button, Row, Col } from "react-bootstrap";
import Apis, { endpoints, authApis } from "../configs/Apis";


const Stats = () => {
  const [stats, setStats] = useState([]);
  const [timeStats, setTimeStats] = useState([]);
  const [topUsers, setTopUsers] = useState([]);
  const [day, setDay] = useState(new Date().getDate());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [year, setYear] = useState(new Date().getFullYear());
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchStats = async (d = day, m = month, y = year) => {
    try {
      setLoading(true);
      let res = await Apis.get(`${endpoints['stats']}?day=${d}&month=${m}&year=${y}`);
      setStats(res.data.stats || []);
      setTimeStats(res.data.timeStats || []);
      setTopUsers(res.data.topUsers || []);
      setDay(res.data.day);
      setMonth(res.data.month);
      setYear(res.data.year);
    } catch (err) {
      console.error(err);
      setError("Không thể tải thống kê.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStats();
  }, []);

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  const [totalRevenue, bookingCount] =
    timeStats && timeStats[0] ? timeStats[0] : [0, 0];

  return (
    <div>
      {/* Doanh thu theo bãi */}
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <Card.Title>Doanh thu theo bãi</Card.Title>
          {stats.length === 0 ? (
            <Alert variant="info">Không có dữ liệu.</Alert>
          ) : (
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Bãi</th>
                  <th>Doanh thu (VNĐ)</th>
                </tr>
              </thead>
              <tbody>
                {stats.map(([lot, revenue], idx) => (
                  <tr key={idx}>
                    <td>{lot}</td>
                    <td>{revenue?.toLocaleString() ?? "0"}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )}
        </Card.Body>
      </Card>

      {/* Doanh thu theo ngày + bộ lọc */}
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <Card.Title>Doanh thu theo ngày</Card.Title>

          {/* Bộ lọc nằm ngay đây */}
          <Row className="mb-3">
            <Col xs={4}>
              <Form.Control
                type="number"
                min="1"
                max="31"
                value={day}
                onChange={(e) => setDay(e.target.value)}
              />
            </Col>
            <Col xs={4}>
              <Form.Control
                type="number"
                min="1"
                max="12"
                value={month}
                onChange={(e) => setMonth(e.target.value)}
              />
            </Col>
            <Col xs={4}>
              <Form.Control
                type="number"
                min="2000"
                value={year}
                onChange={(e) => setYear(e.target.value)}
              />
            </Col>
          </Row>
          <Button
            variant="primary"
            className="mb-3"
            onClick={() => fetchStats(day, month, year)}
          >
            Lọc
          </Button>

          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Tổng doanh thu (VNĐ)</th>
                <th>Số lượt đặt</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{(totalRevenue ?? 0).toLocaleString()}</td>
                <td>{bookingCount ?? 0}</td>
              </tr>
            </tbody>
          </Table>
        </Card.Body>
      </Card>

      {/* Top khách hàng */}
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <Card.Title>Top khách hàng</Card.Title>
          {topUsers.length === 0 ? (
            <Alert variant="info">Không có dữ liệu.</Alert>
          ) : (
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Tên</th>
                  <th>Doanh thu (VNĐ)</th>
                </tr>
              </thead>
              <tbody>
                {topUsers.map(([name, revenue], idx) => (
                  <tr key={idx}>
                    <td>{name}</td>
                    <td>{revenue?.toLocaleString() ?? "0"}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )}
        </Card.Body>
      </Card>
    </div>
  );
};

export default Stats;
