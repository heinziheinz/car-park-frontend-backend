import {cloneElement} from "react";

const UsersReservation = ({userReservations}) => {
    console.log(userReservations[0].user)
   return (
        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="user">User</th>
                    <th name="car">Car</th>
                    <th name="StartDate">Start Date</th>
                    <th name="EndDate">End Date</th>
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
        </div>
    )
}
export default UsersReservation;