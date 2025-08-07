import { useContext, useEffect, useState } from "react";
import { Button, Container, Nav, Navbar } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis"
import { Link } from "react-router-dom";
import { MyUserContext } from "../../configs/Context";
import { FaBell } from "react-icons/fa";


const Header = () => {
  const [parkinglots, setParkingLots] = useState([]);
  const [user, dispatch] = useContext(MyUserContext);

  useEffect(() => {
    const loadParkingLots = async () => {
      let res = await Apis.get(endpoints['parkinglots']);
      setParkingLots(res.data);
    }
  }, []);

  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Link to="/" className="nav-link">Trang chu</Link>
            <Link to="/mybookings" className="nav-link">My Bookings</Link>
            {user === null ? <>
              <Link to="/login" className="nav-link">Login</Link>
              <Link to="/register" className="nav-link">Register</Link>
            </> : <>
              <Link to="/myplates" className="nav-link">My Plates</Link>
              <Link to="/notifications" className="nav-link">
                <FaBell size={20} title="Thông báo" />
              </Link>
              <Link to="/myprofile" className="nav-link text-info">
                <img src={user.avatar} width={30} className="reminded" />
                Chao {user.username}
              </Link>
              <Button variant="danger" onClick={() => dispatch({ "type": "logout" })}>Đăng xuất</Button></>}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar >
  );
}

export default Header;