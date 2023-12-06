import {useEffect} from "react";
import {useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import Loading from "../Components/Loading/Loading.jsx";
import CarForm from "../Components/CarForm/CarForm.jsx";
import {fetchAuthenticated} from "../Utilities/api.js";


const CarUpdater = () => {
    const [car, setCar] = useState(null);
    const [loading, setLoading] = useState(true);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            try {
                const carToBeUpdated = await fetchAuthenticated(`/cars/id/${id}`, {
                    method: "GET"
                });
                if (carToBeUpdated.ok) {
                    const carToBeUpdatedParsed = await carToBeUpdated.json();
                    setCar(carToBeUpdatedParsed);
                    setLoading(false);
                }
            } catch (err) {
                console.log(err);
            }
        })();
    }, [id]);

    const updateCarHandler = async (car) => {
        try {
            const updatedCar = await fetchAuthenticated(`/cars/${id}`, {
                method: "PUT",
                body: JSON.stringify(car)
            });
            if (updatedCar.ok) {
                navigate("/car-list")
            }
        } catch (err) {
            console.log(err);
        }
    }

    if (loading) {
        return <Loading/>;
    }


    return <CarForm
        car={car}
        onSave={updateCarHandler}
    />;
}
export default CarUpdater;