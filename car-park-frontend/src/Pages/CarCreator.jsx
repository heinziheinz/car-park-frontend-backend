import {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import CarForm from "../Components/CarForm.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import {loadJson} from "../Utilities/loadJson.js"

const CarCreator = () => {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const handleCreateCar = async (car) => {
        setLoading(true);
        const option = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest',
            },
            mode: 'cors',
            cache: 'default',
            body: JSON.stringify(car),
        };

        const data = await loadJson(`/cars`, option)
        console.log(data);
        setLoading(false);
        navigate("/");

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

