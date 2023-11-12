import {Link} from "react-router-dom";
import {cloneElement, useContext} from "react";
import {LogginInContext} from "../../main.jsx";

const CarTable = ({cars, startDate, endDate, userID}) => {
    const {loggedIn} = useContext(LogginInContext);
    console.log()
    return (
        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="name">name</th>
                    <th name="type">price</th>
                </tr>
                </thead>
                <tbody>
                {cars.map((car) => (
                    <tr key={car.id}>
                        <td>{car.typeName}</td>
                        <td>{car.price}</td>
                        <td><img src={car.image} width="200" height="100"/></td>
                        <td>
                            <Link
                                to={loggedIn ? `/booking-confirmation/${car.id}/${startDate}/${endDate}/${car.typeName}/${car.price}/${userID}` : "/login"}>
                                <button type="button">{loggedIn ? "Book Car" : "Log In to Book Car"}</button>
                            </Link>
                        </td>

                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}
export default CarTable;