import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import Login from "./components/Login";
import Register from "./components/Register";
import { MyUserContext } from "./configs/Context";
import { useReducer, useEffect } from "react";
import MyUserReducer from "./reducers/MyUserReducer.js";
import ParkingLotDetails from "./components/ParkingLotDetails";
import cookie from 'react-cookies';
import Apis, { endpoints } from "./configs/Apis";
import MyBooking from "./components/MyBooking";
import MyProfile from "./components/MyProfile.js";
import MyPlate from "./components/MyPlate.js";
import Notification from "./components/Notification.js";
import StatsPage from "./components/Stats.js";

const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  useEffect(() => {
    const loadUser = async () => {
      const token = cookie.load("token");
      if (token) {
        try {
          let res = await Apis.get(endpoints['current-user'], {
            headers: {
              "Authorization": `Bearer ${token}`
            }
          });
          dispatch({
            type: "login",
            payload: res.data
          });
        } catch (err) {
          console.error("Không thể load user từ token:", err);
          cookie.remove("token");
        }
      }
    };

    loadUser();
  }, []);

  return (
    <MyUserContext.Provider value={[user, dispatch]}>
      <BrowserRouter>
        <Header />

        <Container className="my-4">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/parkinglots/:lotId" element={<ParkingLotDetails />} />
            <Route path="/mybookings" element={<MyBooking />} />
            <Route path="/myprofile" element={<MyProfile />} />
            <Route path="/myplates" element={<MyPlate />} />
            <Route path="/notifications" element={<Notification />} />
            <Route path="/stats" element={<StatsPage />} />
          </Routes>
        </Container>

        <Footer />
      </BrowserRouter>
    </MyUserContext.Provider>
  );
};

export default App;
