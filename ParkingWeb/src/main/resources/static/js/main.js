function deleteParkingLot(endpoint) {
    if (confirm("Ban chac chan xoa?") === true) {
        fetch("/ParkingWeb" + endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert(res.status);
        });
    }
}

function deleteReview(url) {
    if (confirm("Bạn có chắc chắn muốn xóa đánh giá này?") === true) {
        fetch("/ParkingWeb" + url, {
            method: "DELETE"
        })
        .then(res => {
            if (res.status === 204) {
                location.reload();
            } else {
                alert(res.status);
            }
        });
    }
}

window.deleteReview = deleteReview;


function deleteSlot(endpoint) {
    if (confirm("Ban chac chan xoa?") === true) {
        fetch("/ParkingWeb" + endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert(res.status);
        });
    }
}

function deleteBooking(endpoint) {
    if (confirm("Ban chac chan xoa?") === true) {
        fetch("/ParkingWeb" + endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert(res.status);
        });
    }
}

function deleteUser(endpoint) {
    if (confirm("Ban chac chan xoa?") === true) {
        fetch("/ParkingWeb" + endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert(res.status);
        });
    }
}

function deletePlate(endpoint) {
    if (confirm("Bạn có chắc chắn muốn xóa biển số này?") === true) {
        fetch("/ParkingWeb" + endpoint, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204) location.reload();
            else alert("Xóa không thành công!");
        });
    }
}




document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    if (!form) return;

    form.addEventListener("submit", function (e) {
        const start = new Date(document.getElementById("startTime").value);
        const end = new Date(document.getElementById("endTime").value);

        if (start >= end) {
            alert("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc.");
            e.preventDefault();
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('updateSlotModal');
    if (modal) {
        modal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;

            const id = button.getAttribute('data-id');
            const code = button.getAttribute('data-code');
            const status = button.getAttribute('data-status');

            document.getElementById('modalSlotId').value = id;
            document.getElementById('modalSlotCode').value = code;
            document.getElementById('modalSlotStatus').value = status;
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('updatePlateModal');
    if (modal) {
        modal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;

            const id = button.getAttribute('data-id');
            const license = button.getAttribute('data-license');

            const plateIdInput = document.getElementById('modalPlateId');
            const plateInput = document.getElementById('modalLicensePlate');
            const updateForm = document.getElementById('updatePlateForm');

            plateIdInput.value = id;
            plateInput.value = license;

            updateForm.action = `/ParkingWeb/licenseplate/update/${id}`;
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const bookingModal = document.getElementById('updateBookingModal');
    if (bookingModal) {
        bookingModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const bookingId = button.getAttribute('data-id');
            const status = button.getAttribute('data-status');

            const idInput = document.getElementById('modalBookingId');
            const statusSelect = document.getElementById('modalBookingStatus');
            const form = document.getElementById('updateBookingForm');

            idInput.value = bookingId;
            statusSelect.value = status;

            form.action = `/ParkingWeb/booking/update/${bookingId}`;
        });
    }
});








function disableSubmit(form) {
    const btn = form.querySelector("button[type='submit']");
    if (btn) {
        btn.disabled = true;
        btn.innerText = "Đang gửi...";
    }
}
window.disableSubmit = disableSubmit;


