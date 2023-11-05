import {Outlet, Link} from "react-router-dom";
import {useContext} from "react";
import {LogginInContext} from "../../main.jsx";
import {UserRoleContext} from "../../main.jsx";

const Layout = () => {
    const {loggedIn} = useContext(LogginInContext);
    const {userRole} = useContext(UserRoleContext);
    return (<div>
        <h2>Car Park</h2>
        <nav>
            <li>
                {loggedIn ? <Link to="/logout">
                    <button type="button">LoginOut</button>
                </Link> : <Link to="/login">
                    <button type="button">LogIn</button>
                </Link>}
                {loggedIn && userRole === "ADMIN" ? <Link to="/add-a-car">
                    <button type="button">Add Car</button>
                </Link> : ""}
                {loggedIn && userRole === "ADMIN" ? <Link to="/show-all-cars">
                    <button type="button">Show all cars</button>
                </Link> : ""}
                {loggedIn && userRole === "ADMIN" ? <Link to="/show-all-users">
                    <button type="button">Show all users</button>
                </Link> : ""} {loggedIn && userRole === "ADMIN" || loggedIn && userRole === "USER" ?
                <Link to="/all-reserved-cars">
                    <button type="button">All reserved cars</button>
                </Link> : ""}
            </li>
        </nav>

        <Outlet/>
    </div>);
}

export default Layout;