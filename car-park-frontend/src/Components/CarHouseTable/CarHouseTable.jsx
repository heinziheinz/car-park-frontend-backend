import {Link} from "react-router-dom";

import{cloneElement} from "react";

const CarHouseTable = ({carHouses, onDelete, children}) => {
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
                            {cloneElement(children, { url: `/car-house-update/${carHouse.id}`, value: "Update CarHouse" })}
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