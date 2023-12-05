import {useEffect, useState} from "react";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";
import Loading from "../Components/Loading/Loading.jsx";
import {useNavigate} from "react-router-dom";
import {Link} from "react-router-dom";
import UsersReservationTable from "../Components/UsersReservationTable/UsersReservationTable.jsx";
import {fetchAuthenticated} from "../Utilities/api.js";

const AllReservedCars = () => {

    const [allReservedCars, setAllReservedCars] = useState([]);
    const [loading, setLoading] = useState(true);
    const [reload, setReload] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {

            setLoading(true);
            const options = {
                method: "GET",
            };
            const userData = JSON.parse(localStorage.getItem("userdata"));
            console.log("userData")
            console.log(userData.userId)
            const headers = {
                "Authorization": `Bearer ${userData.jwt}`,
                "Content-Type": "application/json",
                "credentials": "include"
            };///reservation/get-all-reserved-cars/id/
            const responseAllReservedCars = await jwtTokenFetch(`/reservation/get-all-reserved-cars-reservations-user/id/${userData.userId}?page=0&size=10`, options, headers);
            console.log("allReservedCars " + responseAllReservedCars);
            console.log(responseAllReservedCars);
            if (responseAllReservedCars.ok) {
                console.log("ALL OK");
                const allReservedCarsParsed = await responseAllReservedCars.json()
                console.log(allReservedCarsParsed.content);
                setAllReservedCars(allReservedCarsParsed.content);
                setLoading(false);
            }
        })();
    }, [reload]);

    const deleteReservationHandler = async (reservationId) => {
        console.log(reservationId);
        console.log('HLLLOOOOOOOOO');
        try {
            //reservation/get-all-reserved-cars-reservations-user/id/{userId}
            const deletedReservation = await fetchAuthenticated(`/reservation/${reservationId}`, {
                method: "DELETE"

            });
            console.log("deletedReservation")
            console.log(deletedReservation)
            console.log(deletedReservation.ok)
            if (deletedReservation.ok) {
                console.log("INSIDEEEE")
                setReload(!reload);
                // navigate("/");
            } else {
                throw new Error("CarHouseError");
            }
            console.log("DAHINTER")
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

    console.log(allReservedCars);
    return (
        <UsersReservationTable
            userReservations={allReservedCars}
            onDelete={deleteReservationHandler}
        />
    );
}
export default AllReservedCars;