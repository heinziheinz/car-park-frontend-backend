import {cloneElement, useContext} from "react";
import {Link} from "react-router-dom";
import {LogginInContext} from "./../../main.jsx";

const UsersReservationTable = ({userReservations}) => {
    const {loggedIn} = useContext(LogginInContext);
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
                    <th name="Change Reservation">Change Reservation</th>

                </tr>
                </thead>
                <tbody>

                    {userReservations.map((userData, index)=>{
                        return(
                            <tr key={userData.car.id}>
                            <td>{userData.car.typeName}</td>
                            <td>{userData.startDate}</td>
                            <td>{userData.endDate}</td>
                            <td>{userData.car.price}</td>
                                <td>
                                    <Link
                                        to={loggedIn ? `/change-reservations/${userData.reservationId}` : "/login"}>
                                        <button>Change Reservation</button>
                                    </Link>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    )
}
export default UsersReservationTable;