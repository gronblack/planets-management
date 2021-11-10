function setModalFields(modalBlock, data) {
    if (data == null) {
        return;
    }

    modalBlock.find('[name="id"]').val(data['id']);
    modalBlock.find('[name="name"]').val(data['name']);
    modalBlock.find('[name="age"]').val(data['age']);
    let planetsText = [];
    data['planetTos'].forEach(function(planet) {
        planetsText.push(planet['name'] + " (" + planet['id'] + ")");
    });
    modalBlock.find('[name="planets"]').val(planetsText.join("\n"));
}