import {useParams} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import {fetchAuthenticated} from "../Utilities/api.js";
import Loading from "../Components/Loading/Loading.jsx";
import ReservationForm from "../Components/ReservationForm/ReservationForm.jsx";


const ReservationUpdate = () => {
    const {reservationId} = useParams();
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

    const updateHandler = async (car) => {
        console.log("Update Handler");
        console.log(car);
    }
    if (loading) {
        return <Loading/>;
    }
    return <ReservationForm reservation={reservation} onSave={updateHandler}/>;
}
export default ReservationUpdate;