import React, { useEffect, useState } from "react";
import { Container, Card, Alert, Button } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const Notification = () => {
    const [notiList, setNotiList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchNotis = async () => {
            try {
                const res = await authApis().get(endpoints["notifications"]);
                setNotiList(res.data);
            } catch (err) {
                console.error(err);
                setError("Không thể tải danh sách thông báo.");
            } finally {
                setLoading(false);
            }
        };

        fetchNotis();
    }, []);

    if (loading) return <MySpinner />;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h2 className="mb-4">Thông báo của bạn</h2>
            {notiList.length === 0 ? (
                <Alert variant="info">Không có thông báo nào.</Alert>
            ) : (
                notiList.map((n) => (
                    <Card key={n.notificationId} className="mb-3 shadow-sm">
                        <Card.Body>
                            <Card.Text><strong>Nội dung:</strong> {n.message || "Không có nội dung"}</Card.Text>
                            <Card.Text>
                                <strong>Thời gian:</strong>{" "}
                                {n.sentAt
                                    ? new Date(n.sentAt).toLocaleString()
                                    : "Không rõ"}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                ))
            )}
        </Container>
    );
};

export default Notification;
