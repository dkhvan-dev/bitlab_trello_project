package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.Folders;

import java.util.List;

public interface FoldersService {
    List<Folders> allFolders();
    Folders addFolder(Folders folder);
    Folders detailsFolder(Long id);
    void deleteFolder(Long folderId);
    Folders editFolderTitle(Long folderId, String folderTitle);
}
