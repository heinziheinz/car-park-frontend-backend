import {Link} from "react-router-dom";

//const UserTable = ({users, onDelete, isBoxChecked}) => {
const UserTable = ({users, onDelete}) => {
    console.log(users)
    return (

        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="firstName">Name</th>
                    <th name="level">Adress</th>
                    <th name="position">Birthdate</th>
                    {/*<th name="present">Blocked</th>*/}
                    <th/>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td>{user.name}</td>
                        <td>{user.address}</td>
                        <td>{user.birthdate}</td>
                        {/* <td>
                            <input
                                name={user.id}
                                type="checkbox"
                                checked={user.present}
                                onChange={isBoxChecked}
                            />
                        </td>*/}
                        <td>
                            <Link to={`/update/user/${user.id}`}>
                                <button type="button">Update</button>
                            </Link>
                            <button type="button" onClick={() => onDelete(user.id)}>
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
};

export default UserTable;
