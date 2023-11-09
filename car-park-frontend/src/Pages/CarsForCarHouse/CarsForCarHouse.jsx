import CarsForCarHouseList from "../../Components/CarsForCarHouseList/CarsForCarHouseList.jsx";
import FlipButtons from "../../Components/FlipButtons/FlipButtons.jsx";


const CarsForCarHouse = ({currentPage, totalPages, setCurrentPage, cars, actionHandler, valueForActionButton}) => {

    const flipThePage = (event) => {
        let myCurrentPage;
        if (event.target.name === "plus") {
            myCurrentPage = currentPage + 1;
        }
        if (event.target.name === "minus") {
            myCurrentPage = currentPage - 1;
        }
        setCurrentPage(myCurrentPage);
    }

    return (

        <>
            <CarsForCarHouseList
                cars={cars}
                valueForActionButton={valueForActionButton}
                actionHandler={actionHandler}
            />
            <FlipButtons
                flipThePage={flipThePage}
                currentPage={currentPage}
                totalPages={totalPages}
            />
        </>
    );

}
export default CarsForCarHouse;