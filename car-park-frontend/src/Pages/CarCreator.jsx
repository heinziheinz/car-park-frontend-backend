import {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import CarForm from "../Components/CarForm.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import {loadJson} from "../Utilities/loadJson.js"
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import {findRole} from "../Utilities/findRole.js";

const CarCreator = () => {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const handleCreateCar = async (car) => {

        const options = {
            method: 'POST',
            body: JSON.stringify(car)
        };
        const userData = JSON.parse(localStorage.getItem("userdata"));
        const headers = {
            "Authorization": `Bearer ${userData.jwt}`,
            "Content-Type": "application/json"
        };
        try {
            const savedCar = await jswTokenFetch(`/cars`, options, headers)

            if (savedCar.ok) {
                const savedCarParsed = await savedCar.json();
                console.log("savedCar" + savedCar);

                //TODO: go to car list
                //navigate(`/booking-confirmation/${savedCar.id}`)
            }
        } catch (err) {
            console.log(err);
        }

    };
    if (loading) {
        return <Loading/>;
    }
    return (
        <CarForm
            onSave={handleCreateCar}
        />
    );
}
export default CarCreator;

