import CarTableCompleteList from "../../Components/CarTableCompleteList/CarTableCompleteList.jsx";
import Loading from "../../Components/Loading/Loading.jsx";

import useAllCars from "./useCars.jsx"


const CarList = () => {
    const {cars, handleDelete, loading, totalPages, currentPage, flipThePage} = useAllCars();


    if (loading) {
        return <Loading/>;
    }
    return (
        <>
            <CarTableCompleteList cars={cars} onDelete={handleDelete}/>
            <div>
                <button
                    onClick={flipThePage}
                    name="minus"
                    disabled={currentPage === 0}>Previous
                </button>
                <button
                    onClick={flipThePage}
                    name="plus" disabled={currentPage === totalPages - 1}>Next
                </button>
            </div>
        </>
    );
}
export default CarList;