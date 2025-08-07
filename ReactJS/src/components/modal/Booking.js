import { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import Apis, { endpoints, authApis } from "../../configs/Apis"
import cookies from 'react-cookies';

const BookingModal = ({ showModal, setShowModal, selectedLot }) => {
    const [slots, setSlots] = useState([]);
    const [plates, setPlates] = useState([]);
    const [slotId, setSlotId] = useState("");
    const [licensePlateId, setLicensePlateId] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");

    const [paymentMethod, setPaymentMethod] = useState("");
    const [paymentMethods, setPaymentMethods] = useState([]);


    useEffect(() => {

        if (selectedLot?.lotId) {
            const loadData = async () => {
                let res1 = await Apis.get(endpoints['parkingslots'] + `?lotId=${selectedLot.lotId}`);
                let res2 = await authApis().get(endpoints['plates']);
                let res3 = await Apis.get(endpoints['payment-methods']);
                setSlots(res1.data);
                setPlates(res2.data);
                setPaymentMethods(res3.data);
            };
            loadData();
        }
    }, [selectedLot]);

    const booking = async (e) => {
        e.preventDefault();
        console.log(cookies.load("token"));

        console.log("📦 Dữ liệu gửi đi:", {
            slotId: { slotId: slotId, lotId: { lotId: selectedLot.lotId, pricePerHours: selectedLot.pricePerHour } },
            licensePlateId: { id: licensePlateId },
            startTime,
            endTime,
            payment: {
                    paymentMethod: paymentMethod 
                }

        });
        try {
            const res = await authApis().post(endpoints['add-booking'], {
                slotId: { slotId: slotId, lotId: { lotId: selectedLot.lotId, pricePerHours: selectedLot.pricePerHour } },
                licensePlateId: { id: licensePlateId },
                startTime,
                endTime,
                payment: {
                    paymentMethod: paymentMethod 
                }
            });
            alert("Đặt chỗ thành công!");
            setShowModal(false);
        } catch (err) {
            console.error(err);
            alert("Lỗi khi đặt chỗ!");
        }
    };

    return (
        <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Đặt chỗ: {selectedLot?.name}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={booking}>
                    {/* Chọn chỗ đậu */}
                    <Form.Group className="mb-3">
                        <Form.Label>Chọn chỗ đậu</Form.Label>
                        <Form.Select value={slotId} onChange={(e) => setSlotId(e.target.value)} required>
                            <option value="">-- Chọn chỗ đậu --</option>
                            {slots.map(s => (
                                <option key={s.slotId}
                                    value={s.slotId}
                                    disabled={s.status !== "AVAILABLE"}
                                    style={{ color: s.status === "AVAILABLE" ? "green" : "gray" }}>
                                    {s.slotCode} - {s.status}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>

                    {/* Chọn biển số xe */}
                    <Form.Group className="mb-3">
                        <Form.Label>Biển số xe</Form.Label>
                        <Form.Select value={licensePlateId} onChange={(e) => setLicensePlateId(e.target.value)} required>
                            <option value="">-- Chọn biển số xe --</option>
                            {plates.map(p => (
                                <option key={p.id} value={p.id}>
                                    {p.licensePlate}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>

                    {/* Thời gian bắt đầu */}
                    <Form.Group className="mb-3">
                        <Form.Label>Thời gian bắt đầu</Form.Label>
                        <Form.Control
                            type="datetime-local"
                            value={startTime}
                            onChange={(e) => setStartTime(e.target.value)}
                            required
                        />
                    </Form.Group>

                    {/* Thời gian kết thúc */}
                    <Form.Group className="mb-3">
                        <Form.Label>Thời gian kết thúc</Form.Label>
                        <Form.Control
                            type="datetime-local"
                            value={endTime}
                            onChange={(e) => setEndTime(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Phương thức thanh toán</Form.Label>
                        <Form.Select value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)} required>
                            <option value="">-- Chọn phương thức --</option>
                            {paymentMethods.map(pm => (
                                <option key={pm} value={pm}>{pm.replace("_", " ").toUpperCase()}</option>
                            ))}
                        </Form.Select>
                    </Form.Group>


                    <Button type="submit" variant="success">Xác nhận đặt chỗ</Button>
                    <Button variant="secondary" className="ms-2" onClick={() => setShowModal(false)}>Hủy</Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default BookingModal;
