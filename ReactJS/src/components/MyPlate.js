import React, { useEffect, useState } from "react";
import Apis, { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";
import { Container, Card, Alert, Button, Modal, Form } from "react-bootstrap";

const MyPlate = () => {
    const [plates, setPlates] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const [showModal, setShowModal] = useState(false);
    const [newPlate, setNewPlate] = useState("");

    useEffect(() => {
        const fetchPlates = async () => {
            try {
                const res = await authApis().get(endpoints["plates"]);
                setPlates(res.data);
            } catch (err) {
                console.error(err);
                setError("Không thể tải danh sách biển số.");
            } finally {
                setLoading(false);
            }
        };

        fetchPlates();
    }, []);


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await authApis().post(endpoints["add-plate"], {
                licensePlate: newPlate.trim(),
            });
            setPlates([...plates, res.data]);
            setNewPlate("");
            setShowModal(false);
        } catch (err) {
            console.error(err);
            alert("Không thể thêm biển số.");
        }
    };

    if (loading) return <MySpinner />;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h2 className="mb-4">Biển số xe của bạn</h2>
            {plates.length === 0 ? (
                <Alert variant="info">Bạn chưa có biển số xe nào.</Alert>
            ) : (
                plates.map((p) => (
                    <Card key={p.id} className="mb-3 shadow-sm">
                        <Card.Body>
                            <Card.Text><strong>Biển số:</strong> {p.licensePlate}</Card.Text>
                            <Card.Text><strong>Ngày tạo:</strong> {new Date(p.createdAt).toLocaleString()}</Card.Text>
                        </Card.Body>
                    </Card>
                ))
            )}
            <Button variant="success" className="mt-3" onClick={() => setShowModal(true)}>
                + Thêm biển số mới
            </Button>


            <Modal show={showModal} onHide={() => setShowModal(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Thêm biển số xe</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="licensePlate">
                            <Form.Label>Biển số</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Nhập biển số xe"
                                value={newPlate}
                                onChange={(e) => setNewPlate(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" className="mt-3">
                            Lưu
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </Container>
    );
};

export default MyPlate;
