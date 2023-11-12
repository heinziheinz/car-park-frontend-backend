import {useParams, useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {fetchAuthenticated} from "../Utilities/api.js";
import Loading from "../Components/Loading/Loading.jsx";
import ReservationForm from "../Components/ReservationForm/ReservationForm.jsx";
import {jwtTokenFetch} from "../Utilities/jwtTokenFetch.js";


const ReservationUpdate = () => {
    const {reservationId} = useParams();
    const navigate = useNavigate();
    console.log("reservationId")
    console.log(reservationId)
    const [loading, setLoading] = useState(true);
    const [reservation, setReservation] = useState(null);

    useEffect(() => {

        (async () => {
            try {
                const reservationReceived = await fetchAuthenticated(`/reservation/id/${reservationId}`, {
                    method: "GET"
                });
                console.log(reservationReceived)
                if (reservationReceived.ok) {
                    const reservationParsed = await reservationReceived.json();
                    console.log(reservationParsed);
                    setReservation(reservationParsed);
                    setLoading(false);
                } else {
                    throw new Error("All carts error");
                }
            } catch (err) {
                console.error(err);
            }
        })()
    }, []);

    const updateReservationHandler = async (reservation) => {
        try {
            //TODO:Morgen weiter wird nicht wirklich upgedated
            setLoading(true);
            //http://localhost:8080/users/id/3
            const updatedUser = await fetchAuthenticated(`/reservation/${reservationId}`, {
                method: "PUT",
                body: JSON.stringify(reservation)
            })
            console.log(updatedUser);
            if (updatedUser.ok) {
                const updatedUserParsed = await updatedUser.json();
                setLoading(false);
                navigate("/show-all-users")
            }
        } catch (err) {
            console.error(err);
        }
    }
    if (loading) {
        return <Loading/>;
    }
    return <ReservationForm reservation={reservation} onSave={updateReservationHandler}/>;
}
export default ReservationUpdate;