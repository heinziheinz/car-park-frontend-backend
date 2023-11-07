import {Link} from "react-router-dom";

const CarHouseTable = ({carHouses, onDelete}) => {
    console.log(carHouses);
    return (
        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="name">CarHouse name</th>
                    <th name="adress">Adress</th>
                    <th name="capacity">Capacity</th>
                </tr>
                </thead>
                <tbody>
                {carHouses.map((carHouse) => (
                    <tr key={carHouse.id}>
                        <td>{carHouse.houseName}</td>
                        <td>{carHouse.address}</td>
                        <td>{carHouse.capacity}</td>
                        <td>
                            {/* /equipment/update/:id */}
                            <Link to={`/car-house-update/${carHouse.id}`}>
                                <button type="button">Update CarHouse</button>
                            </Link>
                        </td>
                        <td>
                            <button type="button" onClick={() => onDelete(carHouse.id)}>
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}
export default CarHouseTable;