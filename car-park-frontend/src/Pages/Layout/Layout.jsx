import {Outlet, Link} from "react-router-dom";
import {useContext} from "react";
import {LogginInContext} from "../../main.jsx";
import {UserRoleContext} from "../../main.jsx";
import "./Layout.css";

const Layout = () => {
    const {loggedIn} = useContext(LogginInContext);
    const {userRole} = useContext(UserRoleContext);
    return (
        <div className="Layout">
            <h2>Super Rental</h2>
            <nav>
                <ul>
                    <li>
                        <Link to="/search-car">
                            <button type="button">Reserve Car</button>
                        </Link>
                        {loggedIn ? <Link to="/logout">
                            <button type="button">LogOut</button>
                        </Link> : <Link to="/login">
                            <button type="button">LogIn</button>
                        </Link>}
                        {!loggedIn ?
                            <Link to="/subscribe">
                                <button type="button">Subscribe</button>
                            </Link> : ""}
                        {loggedIn && userRole === "ADMIN" ? <Link to="/add-a-car">
                            <button type="button">Add Car</button>
                        </Link> : ""}
                        {loggedIn && userRole === "ADMIN" ? <Link to="/show-all-users">
                            <button type="button">Show all users</button>
                        </Link> : ""}
                        {loggedIn && userRole === "USER" || loggedIn && userRole === "USER" ?
                            <Link to="/all-reserved-cars">
                                <button type="button">All reserved cars</button>
                            </Link> : ""}
                        {loggedIn && userRole === "ADMIN" ?
                            <Link to="/car-list">
                                <button type="button">Car List</button>
                            </Link> : ""}
                        {loggedIn && userRole === "ADMIN" ?
                            <Link to="/car-house-creator">
                                <button type="button">Add CarHouse</button>
                            </Link> : ""}
                        {loggedIn && userRole === "ADMIN" ?
                            <Link to="/car-house-list">
                                <button type="button">All Car Houses</button>
                            </Link> : ""}

                    </li>
                </ul>
            </nav>

            <Outlet/>
        </div>
    );
}

export default Layout;