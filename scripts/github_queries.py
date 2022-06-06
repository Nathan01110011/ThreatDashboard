

def upsert_into_github_poc_table(repo):
    query = "INSERT INTO github_repos(repo_id, repo_name, date_created, date_updated, stars, url) " \
            f"VALUES({repo.id}, \'{repo.name}\', '{repo.created_at}', '{repo.updated_at}', {repo.stargazers_count}, " \
            f"'{repo.svn_url}') " \
            "ON CONFLICT (repo_id) DO UPDATE" \
            f" SET repo_name = '{repo.name}', date_updated = '{repo.updated_at}', stars = {repo.stargazers_count}, " \
            f"url = '{repo.svn_url}';"
    return query
