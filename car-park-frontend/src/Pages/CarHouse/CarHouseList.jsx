
import Loading from "../../Components/Loading/Loading.jsx";
import CarHouseTable from "../../Components/CarHouseTable/CarHouseTable.jsx";
import LinkComponent from "../../Components/LinkComponent/LinkComponent.jsx";


import userCarHouse from "./useCarHouse.jsx"
const CarHouseList = () => {
    const {carHouses, handleDelete, loading, totalPages, currentPage, flipThePage} = userCarHouse();
    if (loading) {
        return <Loading/>;
    }

    return (
        <>
            <CarHouseTable
                carHouses={carHouses}
                onDelete={handleDelete}
            >{<LinkComponent/>}</CarHouseTable>
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

export default CarHouseList;