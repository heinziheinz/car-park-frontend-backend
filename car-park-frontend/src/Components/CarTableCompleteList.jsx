import { Link } from "react-router-dom";
const CarTableCompleteList = ({cars, onDelete}) => {
    return <div className="EmployeeTable">
        <table>
            <thead>
            <tr>
                <th name="firstName">Car Name</th>
                <th name="lastName">Car Price</th>
                <th name="update">{""}</th>
                <th name="update">Update</th>

            </tr>
            </thead>
            <tbody>
            {cars.map((car) => (
                <tr key={car.id}>
                    <td>{car.typeName}</td>
                    <td>{car.price}</td>
                    <td><img src={car.image} width="200" height="100"/></td>
                    <td>
                        <Link to={`/car/update/${car.id}`}>
                            <button type="button">Update</button>
                        </Link>
                        <button type="button" onClick={() => onDelete(car.id)}>
                            Delete
                        </button>
                    </td>

                </tr>
            ))}
            </tbody>
        </table>
    </div>
}
export default CarTableCompleteList;