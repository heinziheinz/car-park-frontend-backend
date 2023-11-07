export function initializeInputFieldsForKalender(startDate, endDate) {
    return [
        {
            type: 'date',
            className: 'date',
            name: 'startDate',
            label: 'startDate',
            placeholder: '',
            value: startDate
        },
        {
            type: 'date',
            className: 'date',
            name: 'endDate',
            label: 'endDate',
            placeholder: '',
            value: endDate
        },
    ];
}
