
class Cve:
    def __init__(self, cve_id, desc, date_published, score, severity, cwe):
        self.cve_id = cve_id
        self.desc = desc
        self.date_published = date_published
        self.score = score
        self.severity = severity
        self.cwe = cwe

    def __str__(self):
        return '%s(%s)' % (type(self).__name__ ,', '.join('%s=%s' % item for item in sorted(vars(self).items())))
