import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../../Utilities/api.js";
import {useParams} from "react-router-dom";
import Loading from "../../Components/Loading/Loading.jsx";

const UserCars = () => {
    const [loading, setLoading] = useState(true);
    const [reservedCars, setReservedCars] = useState([]);
    const {id} = useParams();

    useEffect(() => {
        (async () => {
            setLoading(true);
            try {
                //reservation/get-all-reserved-cars-reservations-user/id/{userId}
                const allReservedCarsOfUser = await fetchAuthenticated(`/reservation/get-all-reserved-cars-reservations-user/id/${id}`, {
                    method: "GET"
                });
                console.log(allReservedCarsOfUser)
                if (allReservedCarsOfUser.ok) {
                    const allReservedCarsOfUserParsed = await allReservedCarsOfUser.json()
                    setReservedCars(allReservedCarsOfUserParsed);
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
    return "Users Cars";
}
export default UserCars;