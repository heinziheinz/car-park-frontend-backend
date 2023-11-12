import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";
import {useParams} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";
import UsersReservationTable from "../../Components/UsersReservationTable/UsersReservationTable.jsx";

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
    if (userReservations.length <= 0) {
        return <h4>No Reservations</h4>;
    } else {
        return <UsersReservationTable
            userReservations={userReservations}
        />;
    }
}
export default UserCars;