import React, { useEffect, useState } from "react";
import { Card, Container, Alert, Spinner, Image } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";

const MyProfile = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const res = await authApis().get(endpoints['current-user']);
                setUser(res.data);
            } catch (err) {
                console.error(err);
                setError("Không thể tải thông tin người dùng.");
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, []);

    if (loading) return <Spinner animation="border" />;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!user) return null;

    return (
        <Container className="mt-5">
            <h2 className="mb-4 text-center">Thông tin cá nhân</h2>
            <Card className="shadow-sm p-4 text-center">
                {user.avatar && (
                    <Image
                        src={user.avatar}
                        width={120}
                        height={120}
                        className="mb-3 mx-auto d-block"
                        alt="Avatar người dùng"
                    />
                )}
                <Card.Text><strong>Họ tên:</strong> {user.name}</Card.Text>
                <Card.Text><strong>Email:</strong> {user.email}</Card.Text>
                <Card.Text><strong>Số điện thoại:</strong> {user.phone}</Card.Text>
                <Card.Text><strong>Tên đăng nhập:</strong> {user.username}</Card.Text>
                <Card.Text><strong>Role:</strong> {user.role}</Card.Text>
            </Card>
        </Container>
    );
};
export default MyProfile;
