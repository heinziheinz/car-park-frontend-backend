import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";
import {useParams} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";
import UsersReservation from "../../Components/UsersReservation/UsersReservation.jsx";

const UserCars = () => {
    const [loading, setLoading] = useState(true);
    const [userReservations, setUserReservations] = useState([]);
    const {id} = useParams();

    useEffect(() => {
        (async () => {
            setLoading(true);
            try {
                //reservation/get-all-reserved-cars-reservations-user/id/{userId}
                const userReservationsAndCars = await fetchAuthenticated(`/reservation/get-all-reserved-cars-reservations-user/id/${id}`, {
                    method: "GET"

                });
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
    }, []);
    if (loading) {
        return <Loading/>;
    }
    return <UsersReservation
        userReservations={userReservations}
    />;
}
export default UserCars;