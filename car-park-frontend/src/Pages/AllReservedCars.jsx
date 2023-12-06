import {useEffect, useState} from "react";
import Loading from "../Components/Loading/Loading.jsx";
import UsersReservationTable from "../Components/UsersReservationTable/UsersReservationTable.jsx";
import {fetchAuthenticated} from "../Utilities/api.js";

const AllReservedCars = () => {

    const [allReservedCars, setAllReservedCars] = useState([]);
    const [loading, setLoading] = useState(true);
    const [reload, setReload] = useState(true);

    useEffect(() => {
        (async () => {

            setLoading(true);
            const userData = JSON.parse(localStorage.getItem("userdata"));
            const responseAllReservedCars = await fetchAuthenticated(`/reservation/get-all-reserved-cars-reservations-user/id/${userData.userId}?page=0&size=10`, {
                method: "GET"
            });

            if (responseAllReservedCars.ok) {
                const allReservedCarsParsed = await responseAllReservedCars.json()
                setAllReservedCars(allReservedCarsParsed.content);
                setLoading(false);
            }
        })();
    }, [reload]);

    const deleteReservationHandler = async (reservationId) => {
        try {
            const deletedReservation = await fetchAuthenticated(`/reservation/${reservationId}`, {
                method: "DELETE"

            });
            if (deletedReservation.ok) {
                setReload(!reload);
            } else {
                throw new Error("CarHouseError");
            }
        } catch (err) {
            console.error(err);
        }
    }
    if (loading) {
        return <Loading/>;
    }
    if (allReservedCars.length <= 0) {
        return <h2>No Cars reserved</h2>;
    }

    return (
        <UsersReservationTable
            userReservations={allReservedCars}
            onDelete={deleteReservationHandler}
        />
    );
}
export default AllReservedCars;