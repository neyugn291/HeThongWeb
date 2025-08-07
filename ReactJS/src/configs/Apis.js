import axios from "axios";
import cookie from 'react-cookies'


const BASE_URL = "http://localhost:8080/ParkingWeb/api";

export const endpoints = {
    'parkinglots': '/parkinglots',
    'parkinglot': '/parkinglot',
    'parkingslots': '/slots',
    'add-booking': '/secure/booking/add',
    'plates': '/secure/plates',
    'register': '/user/add',
    'login': '/login',
    'profile': '/secure/profile',
    'user-roles':'/roles',
    'current-user':'/secure/profile',
    'add-review':'/secure/review/add',
    'reviews':'/reviews',
    'mybookings':'/secure/bookings',
    'add-plate':'/secure/plate/add',
    'payment-methods':'/payment-methods',
    'payBooking': '/pay',
    'pdf':'/booking/pdf',
    'notifications':'/secure/notifications',
    'delete-booking':'secure/booking/delete',
}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load('token')}`
    }
})

export default axios.create({
    baseURL: BASE_URL
});