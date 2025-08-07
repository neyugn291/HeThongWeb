import { Modal, Button, Form } from "react-bootstrap";
import { useState } from "react";
import { endpoints, authApis } from "../../configs/Apis";


const ReviewModal = ({ showModal, setShowModal, selectedLot }) => {
    const [rating, setRating] = useState(5);
    const [comment, setComment] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const data = {
                lotId: selectedLot.lotId,
                rating: rating,
                comment: comment
            };

            await authApis().post(endpoints["add-review"], data);

            alert("Gửi đánh giá thành công!");
            setShowModal(false);
        } catch (err) {
            console.error(err);
            alert("Có lỗi khi gửi đánh giá!");
        }
    };
    return (
        <Modal show={showModal} onHide={() => setShowModal(false)} centered>
            <Form onSubmit={handleSubmit}>
                <Modal.Header closeButton>
                    <Modal.Title>Viết đánh giá cho: {selectedLot?.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Label>Đánh giá (1-5 sao)</Form.Label>
                        <Form.Control
                            type="number"
                            value={rating}
                            onChange={e => setRating(e.target.value)}
                            min={1}
                            max={5}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Nhận xét</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={3}
                            value={comment}
                            onChange={e => setComment(e.target.value)}
                        />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Đóng
                    </Button>
                    <Button variant="primary" type="submit">
                        Gửi đánh giá
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    );
};

export default ReviewModal;
