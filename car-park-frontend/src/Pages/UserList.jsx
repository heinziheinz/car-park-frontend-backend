import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../Utilities/api.js";
import Loading from "../Components/Loading/Loading.jsx";
import UserTable from "../Components/UserTable/UserTable.jsx";
import FlipButtons from "../Components/FlipButtons/FlipButtons.jsx";

const UserList = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        (async () => {
            setLoading(true);
            try {
                const allUsers = await fetchAuthenticated(`/users?page=${currentPage}&size=10`, {
                    method: "GET"
                });
                if (allUsers.ok) {
                    const allUsersParsed = await allUsers.json()
                    setUsers(allUsersParsed.content)
                    setTotalPages(allUsersParsed.totalPages);
                    setLoading(false);
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, [currentPage]);
    const deleteUserHandler = (userID) => {
        console.log(userID);
    }
    const flipThePage = (event) => {
        let myCurrentPage;
        if (event.target.name === "plus") {
            myCurrentPage = currentPage + 1;
        }
        if (event.target.name === "minus") {
            myCurrentPage = currentPage - 1;
        }
        setCurrentPage(myCurrentPage);
    }

    if (loading) {
        return <Loading/>;
    }
    return (
        <>
            <UserTable
                onDelete={deleteUserHandler}
                users={users}
            />
            {/*{currentPage,  totalPages, flipThePage}*/}
            <FlipButtons
                flipThePage={flipThePage}
                totalPages={totalPages}
                currentPage={currentPage}
            />
        </>
    );
}
export default UserList;