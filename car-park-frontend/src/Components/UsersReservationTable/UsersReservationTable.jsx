import {cloneElement, useContext} from "react";
import {Link} from "react-router-dom";
import {LogginInContext} from "./../../main.jsx";
import {UserRoleContext} from "./../../main.jsx";

const UsersReservationTable = ({userReservations, onDelete}) => {
    console.log(userReservations)
    const {loggedIn} = useContext(LogginInContext);
    const {userRole} = useContext(UserRoleContext);
    return (
        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="user">User</th>
                    <th name="birthdater">birthdate</th>
                    <th name="address">address</th>

                </tr>
                </thead>
                <tbody>

                <tr>
                    <td>{userReservations[0].user.name}</td>
                    <td>{userReservations[0].user.birthdate}</td>
                    <td>{userReservations[0].user.address}</td>
                </tr>

                </tbody>
            </table>
            <table>
                <thead>
                <tr>
                    <th name="cartype">Car type</th>
                    <th name="startdate">Start Date</th>
                    <th name="enddate">End Date</th>
                    <th name="price">Price per Day</th>
                    <th name="Change Reservation">Delete Car Reservation</th>

                </tr>
                </thead>
                <tbody>

                {userReservations.map((userData, index) => {
                    return (
                        <tr key={userData.car.id}>
                            <td>{userData.car.typeName}</td>
                            <td>{userData.startDate}</td>
                            <td>{userData.endDate}</td>
                            <td>{userData.car.price}</td>
                            <td>
                                <button type="button" onClick={() => onDelete(userReservations[0].reservationId)}>
                                    Delete Car Reservation
                                </button>
                            </td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
            <Link to={userRole === "ADMIN" ? "/show-all-users" : "/"}>
                <button type="button">Back to User List</button>
            </Link>
        </div>
    )
}
export default UsersReservationTable;