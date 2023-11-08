import {Link} from "react-router-dom";
import {cloneElement} from "react";

const CarTable = ({cars, startDate, endDate}) => {
    //TODO: table funktionalisieren

    console.log("HELLO CARS")
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
                                to={`/booking-confirmation/${car.id}/${startDate}/${endDate}/${car.typeName}/${car.price}`}>
                                <button type="button">Book Car</button>
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