import {Link} from "react-router-dom";

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
                    <th/>
                </tr>
                </thead>
                <tbody>
                {cars.map((car) => (
                    <tr key={car.id}>
                        <td>{car.typeName}</td>
                        <td>{car.price}</td>
                        <td>
                            {/* /equipment/update/:id */}
                            <Link to={`/booking-confirmation/${car.id}/${startDate}/${endDate}/${car.typeName}/${car.price}`}>
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