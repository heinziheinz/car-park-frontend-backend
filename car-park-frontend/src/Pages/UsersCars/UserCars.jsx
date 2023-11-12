import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";
import {useParams, useNavigate, Link} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";
import UsersReservationTable from "../../Components/UsersReservationTable/UsersReservationTable.jsx";
import FlipButtons from "../../Components/FlipButtons/FlipButtons.jsx";

const UserCars = () => {
    const [loading, setLoading] = useState(true);
    const [userReservations, setUserReservations] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {

        (async () => {
            console.log("currentPage")
            console.log(currentPage)
            setLoading(true);
            try {
                //reservation/get-all-reserved-cars-reservations-user/id/{userId}
                const userReservationsAndCars = await fetchAuthenticated(`/reservation/get-all-reserved-cars-reservations-user/id/${id}?page=${currentPage}&size=10`, {
                    method: "GET"

                });
                console.log("userReservationsAndCars")
                console.log(userReservationsAndCars)
                if (userReservationsAndCars.ok) {
                    const userReservationsAndCarsParsed = await userReservationsAndCars.json()
                    setUserReservations(userReservationsAndCarsParsed.content);
                    console.log(userReservationsAndCarsParsed)
                    setLoading(false);
                }
            } catch (err) {
                console.error(err);
            }
        })()
    }, [currentPage]);

    const deleteReservationHandler = async (reservationId) => {
        console.log(reservationId);
        try {
            //reservation/get-all-reserved-cars-reservations-user/id/{userId}
            const deletedReservation = await fetchAuthenticated(`/reservation/${reservationId}`, {
                method: "DELETE"

            });
            console.log(deletedReservation)
            if (deletedReservation.ok) {
                navigate("/show-all-users");
            }
        } catch (err) {
            console.error(err);
        }
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
    if (userReservations.length <= 0) {
        return (
            <>
                <h4>No Reservations</h4>
                <Link to={"/show-all-users"}>
                    <button type="button">Back to User List</button>
                </Link>
            </>
        );
    } else {
        return (
            <>
                <UsersReservationTable
                    userReservations={userReservations}
                    onDelete={deleteReservationHandler}
                />
                <FlipButtons
                    flipThePage={flipThePage}
                    totalPages={totalPages}
                    currentPage={currentPage}
                />
            </>
        );

    }
}
export default UserCars;