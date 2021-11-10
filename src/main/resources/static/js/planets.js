function setModalFields(modalBlock, data) {
    if (data == null || data['lordTo'] == null) {
        modalBlock.find('[data-input-group-lord]').hide();
        modalBlock.find('[data-input-group-lord-id]').show();

        if (data == null) return;
    } else {
        modalBlock.find('[data-input-group-lord], [data-input-group-lord-id]').show();
    }

    modalBlock.find('[name="id"]').val(data['id']);
    modalBlock.find('[name="name"]').val(data['name']);

    let lord = data['lordTo'];
    if (lord != null) {
        modalBlock.find('[name="lord"]').val(lord.name + " (id=" + lord.id + ", age=" + lord.age + ")");
        modalBlock.find('[name="lordId"]').val(lord.id);
    }
}