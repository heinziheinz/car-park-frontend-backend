import { Outlet, Link } from "react-router-dom";
const Layout = () => {
    return (<div>
    <h2>Car Park</h2>
        <nav>
            <li>
                <Link to="/login">
                    <button type="button">Login</button>
                </Link>
                <Link to="/add-a-car">
                    <button type="button">Add Car</button>
                </Link>
                <Link to="/show-all-cars">
                    <button type="button">Show all cars</button>
                </Link>
                <Link to="/show-all-users">
                    <button type="button">Show all users</button>
                </Link>
            </li>
        </nav>

        <Outlet/>
    </div>);
}

export default Layout;