package com.sdmx.data;

import com.sdmx.support.util.IOUtils;
import com.sdmx.support.util.URLUtils;
import org.jsoup.Jsoup;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "publication")
public class Publication implements java.io.Serializable {

	public static final String STORAGE_PATH = "publication/";

	public static final String DEFAULT_COVER = "resources/img/default/publication.png";

	public static final String COVER_FILENAME = "cover.png";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String title;

	@ManyToMany(targetEntity=Category.class, cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
	@OrderBy("name")
	@JoinTable(
		name="category_publication",
		joinColumns={@JoinColumn(name="category_id")},
		inverseJoinColumns={@JoinColumn(name="publication_id")}
	)
	private List<Category> categories;

	@Column(columnDefinition = "text", nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	private boolean isNotice;

	private Date updated;

	private Date created;

	@Column(columnDefinition = "char", nullable = false, length = 1)
	private char status = '0';

	@OneToOne(mappedBy = "publication")
	private PublicationApproval approval;

	@Transient
	private String[] fillable = {"title", "description", "account", "isNotice", "updated"};

	public Publication(Long id) {
		this.id = id;
	}

	public Publication() {
		created = new Date();
		updated = new Date();
		isNotice = false;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		int limit = 100;
		String textContent = Jsoup.parse(description).text();

		return textContent.length() > limit ? textContent.substring(0, limit)+"..." : textContent;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(boolean is_notice) {
		this.isNotice = is_notice;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public void emptyCategory() {
		categories = new ArrayList<>();
	}

	public void addCategory(Category category) {
		if (categories == null) {
			categories = new ArrayList<>();
		}

		categories.add(category);
	}

	public List<String> getFiles() {
		String location = IOUtils.storagePath(STORAGE_PATH + "/" + id)+ "/";
		return IOUtils.glob(location,"**.pdf");
	}

	public List<String> getFilesUrl() {
		List<String> images = new ArrayList<>();

		for (String imagePath : getFiles()) {
			images.add(URLUtils.getFileUrl(imagePath));
		}

		return images;
	}

	public String getCoverPath() {
		return STORAGE_PATH + id + "/" + COVER_FILENAME;
	}

	public String getCoverUrl() {
		return isCoverExists() ? "/raw?p="+getCoverPath() : DEFAULT_COVER;
	}

	public boolean isCoverExists() {
		File file = new File(IOUtils.storagePath(getCoverPath()));

		return file.exists();
	}

	public void uploadCover(MultipartFile cover) {
		String storagePath = IOUtils.storagePath(STORAGE_PATH + getId());
		IOUtils.upload(cover, storagePath, COVER_FILENAME);
	}

	public void uploadAttachments(MultipartFile[] files) {
		String storagePath = IOUtils.storagePath(STORAGE_PATH + getId());

		for (int i = files.length-1; i >= 0; i--) {
			MultipartFile file = files[i];
			String filename = IOUtils.getUniqueName(IOUtils.getExtension(file.getOriginalFilename()));

			IOUtils.upload(file, storagePath, filename);
		}
	}

	public void deleteFile(String filename) {
		IOUtils.delete(new File(IOUtils.storagePath(STORAGE_PATH + getId()), filename));
	}
}
