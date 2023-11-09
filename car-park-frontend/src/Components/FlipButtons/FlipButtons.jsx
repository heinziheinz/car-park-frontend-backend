const FlipButtons = ({currentPage,  totalPages, flipThePage}) => {
    return (
        <>
            <button
                onClick={flipThePage}
                name="minus"
                disabled={currentPage === 0}>Previous
            </button>
            <button
                onClick={flipThePage}
                name="plus" disabled={currentPage === totalPages - 1}>Next
            </button>
        </>
    );
}
export default FlipButtons;