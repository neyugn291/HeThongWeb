import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Container, Card, Button, Row, Col, Badge, Alert, ListGroup } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";
import BookingModal from "../components/modal/Booking";
import ReviewModal from "../components/modal/Review";


const ParkingLotDetails = () => {
    const { lotId } = useParams();
    const navigate = useNavigate();
    const [parkingLot, setParkingLot] = useState(null);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [showReviewModal, setShowReviewModal] = useState(false);
    const [reviews, setReviews] = useState([]);


    useEffect(() => {
        const loadDetails = async () => {
            try {
                setLoading(true);
                let res = await Apis.get(`${endpoints['parkinglot']}/${lotId}`);
                setParkingLot(res.data);
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        const loadReviews = async () => {
            try {
                let res = await Apis.get(endpoints['reviews'], {
                    params: { lotId },
                });
                setReviews(res.data);
            } catch (err) {
                console.error("Lỗi khi tải đánh giá:", err);
                setReviews([]);
            }

        };
        console.log(endpoints['reviews']);

        loadDetails();
        loadReviews();
    }, [lotId]);

    if (loading) return <MySpinner />;
    if (!parkingLot) return <Container className="mt-4">Không tìm thấy bãi đỗ xe!</Container>;


    return (
        <Container className="mt-5">
            <Card className="shadow-lg border-0 rounded-4">
                <Row className="g-0">
                    <Col md={6}>
                        <Card.Img
                            src={parkingLot.img}
                            alt={parkingLot.name}
                            className="h-100 rounded-start-4 object-fit-cover"
                            style={{ maxHeight: '500px', objectFit: 'cover' }}
                        />
                    </Col>
                    <Col md={6}>
                        <Card.Body className="p-4 d-flex flex-column justify-content-between h-100">
                            <div>
                                <Card.Title className="display-6 fw-bold mb-3">{parkingLot.name}</Card.Title>

                                <Card.Text className="mb-2">
                                    <i className="bi bi-geo-alt-fill text-danger"></i>{' '}
                                    <strong>Địa chỉ:</strong> {parkingLot.address}
                                </Card.Text>

                                <Card.Text className="mb-2">
                                    <i className="bi bi-currency-dollar text-success"></i>{' '}
                                    <strong>Giá mỗi giờ:</strong>{" "}
                                    <Badge bg="warning" text="dark">
                                        {parkingLot.pricePerHour.toLocaleString()} đ/h
                                    </Badge>
                                </Card.Text>

                                <Card.Text className="mt-4">
                                    <strong>Mô tả:</strong><br />
                                    <span className="text-muted">{parkingLot.description}</span>
                                </Card.Text>
                            </div>

                            <div className=" d-flex mt-auto gap-2">
                                <Button variant="success" onClick={() => setShowModal(true)}>
                                    Đặt chỗ
                                </Button>
                                <Button variant="info" className="text-white" onClick={() => setShowReviewModal(true)}>
                                    Đánh giá
                                </Button>
                                <Button variant="dark" onClick={() => navigate("/")}>
                                    Quay về trang chủ
                                </Button>
                            </div>
                        </Card.Body>
                    </Col>
                </Row>
            </Card>

            <BookingModal
                showModal={showModal}
                setShowModal={setShowModal}
                selectedLot={parkingLot} />

            <ReviewModal
                showModal={showReviewModal}
                setShowModal={setShowReviewModal}
                selectedLot={parkingLot}/>

            <h3 className="mt-5">Đánh giá từ người dùng</h3>
            {reviews.length === 0 ? (
                <Alert variant="info">Chưa có đánh giá nào cho bãi đỗ xe này.</Alert>
            ) : (
                <ListGroup className="mb-5">
                    {reviews.map((r, idx) => (
                        <ListGroup.Item key={idx}>
                            <div className="fw-bold">
                                ⭐ {r.rating} sao
                            </div>
                            <div className="fst-italic text-muted">
                                {r.comment || "Không có nhận xét"}
                            </div>
                            {r.userId?.username && (
                                <div className="text-end text-secondary small">
                                    – {r.userId.username}
                                </div>
                            )}
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            )}
        </Container>
    );
};

export default ParkingLotDetails;
