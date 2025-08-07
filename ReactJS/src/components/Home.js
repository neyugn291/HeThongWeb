import { useEffect, useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import cookies from 'react-cookies'
import BookingModal from "../components/modal/Booking";
import MySpinner from "./layout/MySpinner";
import { Link } from "react-router-dom";
import ReviewModal from "./modal/Review";


const Home = () => {
    const [parkinglots, setParkingLots] = useState([]);
    const [loading, setLoading] = useState(true);
    const [kw, setKw] = useState("");
    const [page, setPage] = useState(1);


    const [showModalBooking, setShowModalBooking] = useState(false);
    const [showModalReview, setShowModalReview] = useState(false);
    const [selectedLot, setSelectedLot] = useState(null);



    useEffect(() => {
        const loadParkingLots = async () => {
            let url = `${endpoints['parkinglots']}?page=${page}`;
            if (kw)
                url = `${url}&kw=${kw}`;

            try {
                setLoading(true);
                let res = await Apis.get(url);
                setParkingLots(res.data);
            } catch (ex) {
                console.error(ex);
            } finally {
                setLoading(false);
            }
        };

        loadParkingLots();
        let timer = setTimeout(() => {
            if (page > 0) loadParkingLots();
        }, 500);

        return () => clearTimeout(timer);
    }, [page, kw]);

    useEffect(() => {
        setPage(1);
    }, [kw]);

    const bookingModal = (parkinglot) => {
        setSelectedLot(parkinglot);
        setShowModalBooking(true);
    };

    const reviewModal = (parkinglot) => {
        setSelectedLot(parkinglot);
        setShowModalReview(true);
    };



    return (

        <Container className="mt-4">
            <Form>
                <Form.Group className="mb-3" >
                    <Form.Control value={kw} onChange={e => setKw(e.target.value)} type="text" placeholder="Tim bai do xe" />
                </Form.Group>
            </Form>



            <Row className="g-4">
                {parkinglots.map((p) => (
                    <Col key={p.lotId} xs={12} sm={6} md={4} lg={3}>
                        <Card className="h-100 shadow-sm">
                            <Card.Img
                                variant="top"
                                src={p.img}
                                alt={p.name}
                                style={{ height: '180px', objectFit: 'cover' }}
                            />
                            <Card.Body className="d-flex flex-column">
                                <Card.Title className="text-truncate" title={p.name}>
                                    {p.name}
                                </Card.Title>
                                <Card.Text className="flex-grow-1 small text-wrap" style={{ maxHeight: '100px', overflow: 'hidden' }}>
                                    {p.description}
                                    <br />
                                    <strong>Địa chỉ:</strong> {p.address}
                                    <br />
                                    <strong>Giá:</strong> {p.pricePerHour.toLocaleString()} đ/h
                                </Card.Text>
                                <div className="d-flex mt-auto gap-2">
                                    <Link to={`/parkinglots/${p.lotId}`} className="btn btn-primary btn-sm">Chi tiết</Link>
                                    <Button size="sm" variant="danger" onClick={() => bookingModal(p)}>Đặt chỗ</Button>
                                    <Button size="sm" variant="info" onClick={() => reviewModal(p)} className="text-white">Đánh giá</Button>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {page > 0 && <div className="text-center mt-4 d-flex justify-content-center gap-2">
                <Button variant="secondary" onClick={() => setPage(prev => Math.max(1, prev - 1))} disabled={page === 1}>
                    Trang trước
                </Button>
                <Button variant="primary" onClick={() => setPage(prev => prev + 1)}>
                    Trang sau
                </Button>
            </div>}

            {loading && <MySpinner />}

            <BookingModal
                showModal={showModalBooking}
                setShowModal={setShowModalBooking}
                selectedLot={selectedLot}
            />
            <ReviewModal
                showModal={showModalReview}
                setShowModal={setShowModalReview}
                selectedLot={selectedLot}
            />


        </Container>
    );
};

export default Home;
