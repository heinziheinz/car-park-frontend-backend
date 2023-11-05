import {Link} from "react-router-dom";

const CarTable = ({cars, startDate, endDate, onDelete}) => {
    console.log(cars)
    console.log(startDate)
    console.log(endDate)
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
                            <Link to={`/booking-confirmation/${car.id}/${startDate}/${endDate}`}>
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