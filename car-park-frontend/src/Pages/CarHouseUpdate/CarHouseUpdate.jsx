import CarHouseForm from "../../Components/CarHouseForm/CarHouseForm.jsx";
import Loading from "../../Components/Loading/Loading.jsx";
import CarsForCarHouse from "../CarsForCarHouse/CarsForCarHouse.jsx";
import useCarHouseUpdate from "./useCarHouseUpdate.jsx";
const CarHouseUpdate = () => {
  const {
      setCurrentPageNotAllocatedHandler,
      setCurrentPageInCarHouseHandler,
      adCarToCarHouseHandler,
      removeCarFromCarHouseHandler,
      updateCarHouseHandler,
      carHouse,
      carsNotInCarHouse,
      carsInCarHouse,
      loading,
      totalPagesNotAllocated,
      totalPagesCarsInCarHouse,
      currentPageInCarHouse,
      currentPageNotAllocated
  } = useCarHouseUpdate();

    if (loading) {
        return <Loading/>;
    }
    return <CarHouseForm
        carHouse={carHouse}
        onSave={updateCarHouseHandler}
    >
        {
            <CarsForCarHouse
                cars={carsInCarHouse}
                totalPages={totalPagesCarsInCarHouse}
                currentPage={currentPageInCarHouse}
                setCurrentPage={setCurrentPageInCarHouseHandler}
                actionHandler={removeCarFromCarHouseHandler}
                valueForActionButton={"Remove Car From Car House"}
            />
        }
        {
            <CarsForCarHouse
                cars={carsNotInCarHouse}
                totalPages={totalPagesNotAllocated}
                currentPage={currentPageNotAllocated}
                setCurrentPage={setCurrentPageNotAllocatedHandler}
                actionHandler={adCarToCarHouseHandler}
                valueForActionButton={"Add To Car House"}
            />
        }
    </CarHouseForm>
}
export default CarHouseUpdate;