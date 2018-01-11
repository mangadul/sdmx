package com.sdmx.data;

import com.sdmx.support.util.IOUtils;
import com.sdmx.support.util.URLUtils;
import org.jsoup.Jsoup;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.persistence.*;
import java.io.File;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "news")
public class News implements java.io.Serializable {

	public static final String STORAGE_PATH = "news/";

	public static final String DEFAULT_COVER = "resources/img/default/news.png";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String title;

	@Column(columnDefinition = "text", nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToMany(targetEntity=Category.class, cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
	@OrderBy("name")
	@JoinTable(
		name="category_news",
		joinColumns={@JoinColumn(name="category_id")},
		inverseJoinColumns={@JoinColumn(name="news_id")}
	)
	private Set<Category> categories;

	private Date updated;

	private Date created;

	@Column(columnDefinition = "char", nullable = false, length = 1)
	private char status = '0';

	@OneToOne(mappedBy = "news")
	private NewsApproval approval;

	@Transient
	private String[] fillable = {"title", "content", "account", "updated"};

	public News(Long id) {
		this.id = id;
	}

	public News() {
		created = new Date();
		updated = new Date();
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

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getContent() {
		return content;
	}

	public String getShortContent() {
		int limit = 100;
		String textContent = Jsoup.parse(content).text();

		return textContent.length() > limit ? textContent.substring(0, limit)+"..." : textContent;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public void emptyCategory() {
		categories = new LinkedHashSet<>();
	}

	public void addCategory(Category category) {
		if (categories == null) {
//			 categories = new ArrayList<>();
			categories = new LinkedHashSet<>();
		}

		categories.add(category);
	}

	public List<String> getImages() {
		String location = IOUtils.storagePath(STORAGE_PATH + id)+ "/";
		return IOUtils.glob(location, "**.*");
	}

	public List<String> getImagesUrl() {
		List<String> images = new ArrayList<>();

		for (String imagePath : getImages()) {
			images.add(URLUtils.getRawFileUrl(imagePath));
		}

		return images;
	}

	public String getCoverUrl() {
		List<String> images = getImages();

		if (images.size() > 0) {
			return URLUtils.getRawFileUrl(images.get(0));
		}
		else return DEFAULT_COVER;
	}

	public void upload(MultipartFile[] files) {
		for (int i = files.length-1; i >= 0; i--) {
			MultipartFile file = files[i];

			IOUtils.upload(
				file,
				IOUtils.storagePath(STORAGE_PATH + getId()),
				IOUtils.getUniqueName(IOUtils.getExtension(file.getOriginalFilename()))
			);
		}
	}

	public void deleteFile(String filename) {
		IOUtils.delete(new File(IOUtils.storagePath(STORAGE_PATH + getId()), filename));
	}
}
