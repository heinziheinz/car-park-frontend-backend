import {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import CarForm from "../Components/CarForm/CarForm.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import {fetchAuthenticated} from "../Utilities/api.js";

const CarCreator = () => {
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();
    const handleCreateCar = async (car) => {
        try {
            const savedCar = await fetchAuthenticated(`/cars`, {
                method: 'POST',
                body: JSON.stringify(car)
            });
            if (savedCar.ok) {
                navigate(`/car-list`)
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

