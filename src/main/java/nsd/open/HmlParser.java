package nsd.open;

import java.io.*;

import jakarta.annotation.PostConstruct;
import nsd.open.utils.ConvertToLatex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static nsd.open.utils.ConvertToLatex.parseXml;


@SpringBootApplication
public class HmlParser {

	public static void main(String[] args) throws IOException {

		String s="①";
		ConvertToLatex.symbolToNumber(s);

		SpringApplication.run(HmlParser.class, args);

//		String s ="iVBORw0KGgoAAAANSUhEUgAAAUAAAAFICAIAAACuobCQAABESUlEQVR4Xu2d95dU15Xv5y95HvP0/JbnzXg8jiLJM2PN2OspoGTPWAEjghBBBAkkS7JlJJCEkECASCLnLAGCJnTTme6mE91NdazqnOlERrbfD/32PvvWqVs3nMpV91bt79qr1qU60HWrvvfzPfeefc/fjbNYLNfq74xPsFgs94gNzGK5WGxgFsvFYgOzWC4WG5jFcrHYwCyWi8UGZrFcLDYwi+VisYFZLBeLDcxiuVhsYBbLxWIDs1guFhuYxXKx2MAslovFBmaxXCw2MIvlYrGBWSwXiw3MYrlYbGAWy8ViA7NYLhYbmMVysdjALJaLxQZmsVwsNjCL5WKxgVksF4sNzGK5WGxgFsvFYgOzWC4WG5jFcrHYwCyWi8UGZrFcLDYwi+VisYFZLBeLDcxiuVhsYBbLxWIDs1guFhuYZa3W1tZf/cd/vvj886Ojo8avsRwjNjDLQg8ePPjnf/ynCf/jO1BPP/WU8cssx4gNzLLQJ6s/IfdSHTxwwPgdLGeIDcwyKicnR+9eqtraWuP3sRwgNjArSDDi/ddHfmE2MDzZ29tr/G5WqsUGZgVp/rx5ZvdSwZdgbGz8AVZKxQZmBXT61Clp132799LG9//n9+STW7dsMf4MK6ViA7M0QUKWRt20cVOjp5G2J/7o4dXL18gvlZaWGn+SlTqxgVkoyMb/9dvfkkV/++xvbo/dlgb+/kPf82Q1Pf/E89LDPBh2jtjALBRkY+lPsO7fvv3bjf5B+QwY+MqxqxP/5WH6J1idB8MOERuYNV5bWyu9evL4SXAvld7AUFk7LspnPln9ifG3sFIhNnCmC1gqrxu9+spc6V6zgaE2vrdJPpmTk2P8Xaykiw2c6Xrn7XfIkL+Y+khHW4elgXd+uFt6eO7vXpXPe71e469jJVds4IyWftLVhawLevdCAZDNBq46VSsHw4BuHgynVmzgzFVvb68Mz3948y2De6HgSbOBoXL3F0jbA8CNv5eVRLGBM1dy0hWE59tjtxUGXvfOer2BoXZ8uFt6+PSpU8ZfzUqW2MAZKv2kq6qKKrN7oT5e9RF9A4x7DQaGemPWMvkbuNUhVWIDZ6K8Xq/03r7de83WpTp5/KTCwDAYlrM7IIpz339KxAbOOJknXZmtG46BPWIwLE9oQSA3/k+sxIsNnHHSN+sbrhvZGXjao9PM7qU6uPaI/G3c6pB8sYEzS3aTriyrKL+IvnPijx42W1fWewtXyN/JrQ5JFhs4g6Rv1jdMurIsfUOS2beyDINhbnVIptjAGST9pKsb/YNmx0ZnYE9wqwP3/SdTbOBMkXrSlWVJAz/0ne+aTWuorzadlr+fB8NJExs4I6Rv1v941Udmr1rW7bHb8qfMjjWXvu+fWx2SIzZwRijkpCu7isjAHu77T7rYwOmvgwcOSFNRs374JX+w5Fi52a7m0rc6cN9/EsQGTnOFOenKruTPGvoZFKXv++dWh0SLDZzOMjTrRxSeqewaktSl7/vnVoeEig2czgp/0pVdRWdgT3CrA/f9J05s4LRVaWmptFDISVd2JQ38/uKVZpcqCgbDj/3bY/Sz3PefOLGB01P6SVeWzfph1qaNWhi262dQlL7vn1sdEiQ2cHoq0klXdhWyIUld+r5/XuIwEWIDp6H0zfpF+UVmW4ZfMRrYE9zqwH3/cRcbON0U3aQruzp+9Dj9qkcn/9JsznCK+/4TKjZwWunBgwdy0pW6WT/Mqqqoot8Wsp9BUYa+fz6hFUexgdNKsUy6Cqq/aBueuvrYDezhvv+EiQ2cPtI360cx6cpYD/721/t/a7gebkdhyOIlDhMhNnCaSLFCSjQl3AvlqfVI15k9GWlx33/cxQZOE8U+6UrG5r+Se/Hxr1BxNDAvcRh3sYHTQVE069vU/yPwSvf+9UE8DewJ7vvnJQ5jFxvY9YrXpCsZm/Xu1RP4wq5ssyGjKF7iMI5iA7te+mb9iCddUWy2sS7VL6Y8Qr8/0n4GRemXOOTBcCxiA7tb4ayQEqKU7v3rvb/+YXmUDUmK4iUO4yU2sIuln3S1aeMmozPDKb97xUawe+9p9day+BvYw33/cRIb2K0Kf4UUY4URm6V7/3I3YOBls5abfRhL8RKHsYsN7FZt3bJFfvojnnQVhnv/ItwLjxvWbaD/Jep+BkVx33+MYgO7UhGtkGIsc2y2Ai+599u7fzl+5ETiDGzo++dWh0jFBnafopl0ZRebQ7kX6ujBo4kzsMfU6mB8tSyl2MDuk75ZP4JJV2b32sdmsu639/BREjj26dB2pW914L7/iMQGdpminHQVYWyGkht11dcTbWAPL3EYrdjAblJvb28Ek65iiM3EXqgHd/7iuRafjkJ18RKH0YkN7CZFvEKK2b3hxWbNvbex6qo0Av/wH35gNl4ci/v+oxAb2DWKeNJVDLH52zt/Jfd+e/svrY1t8v81uy6+xUscRio2sDsU7gopcYrNZN0Ht76F+vb2t0kzsIf7/iMUG9gFimzSldm9/tj8N3gyvNj87R3pXtxIpoE9vMRhJGIDu0ARNOvHKTYTeB/cNhL42IaTZr/FvbjvP3yxgZ2u0JOuEhOb9QT+zdPP0R8Q334GRelbHbjvXyE2sKOlb9ZXTboyuzfm2Ky38bKl2ozlpBnYw0schic2sKMV1gopiYnNZF2o+2MP3nz9zeQb2BPc98+tDpZiAztXqklXiY/NWDfRvVBvLH6D/owETYe2K17iMKTYwA5V6BVSzO6Na2y+fxOtSx7e9aXWuJtkA3uClzjkvn+z2MAOVYhJV4mPzdK9UIf3HU6VgT28xKFSbGAnynqFlOTGZtp4ABy+ef/YwWP0x7zw5AtmgyWh9H3/vMShXmxgx0k16crsXnNstrFupLFZc+/YfajTJ8/Q35PQfgZF8RKHdmIDO0uGZv2g8Jzc2CzdC9t1lcnoKFQX9/1big3sLBknXcUSmyV4o4rNZN17o/ehasq1ySQpNLCHlzi0EhvYQSotLZUf0MCkK7N7Q8ZmvXujjc3wPLn3/uiDugqNwN9/6HtmXyWzuO/fIDawU2S9QkrksTkw6I0tNpN10cMj93t8gWtaZlMlubjvXy82sFNknHRlBm847o1fbMaNEXQvPHlv5J5zDKxvdeC+fzawI6Rv1i/KL7Jwb9Jjs+ZeJPA92HCOgT28xKFObODUyzjpyhmx2W/de/DPu8MBAid5OrRd6fv+M3mJQzZwigUJUE66+u2zv7k1ets5sZnYC+6FmvX7WY4ysIeXOBRiA6dY+klXDdcbHRWb746gde8O378zdG/5kuVOM7B+icOM7ftnA6dS+mb9vbv2Oi02Y42ge6Fef+11+jvXvbPe7KVUFS9xyAZOmYImXc2Z68DYTOzFx+EAgVPSz6CoDO/7ZwOnTEGTrlo7HBib0b1+Ah/cfciZBvZk9hKHbODUKKhZ/9yFcMGb3NgcsPHQvSN7tWmMDjRwJvf9s4FToKBJV8vfCte9wbEZK8Gxmax758ZdqIO7DtIfPO3RaWYLpbz0ff8Z1erABk6BAs36Ux4Z7B2MIDb7ratLy0YbxzE2w5PkXtjOOXeZ/ubU9jMoKjOXOGQDJ1v6SVeVVytDg9cUmwNpOcGxWbgXH28P3r1WWuNwA3uCWx0ypO+fDZxU6SddbdrwRWj3pjQ2wzZYFwo2rpU5oqNQXRm4xCEbOHnSr5Dym2efuzV6y+GxWXPvDdyQBH7oO981O8c5lWlLHLKBk6etW7ZI/DZcb1CB1xmxmawrNu7e6BiSf7zZNo6qjOr7ZwMnSUErpBw9oXKvY2KzdO/tgTtQbjGwJ5OWOGQDJ0OGSVduic13RKF7B+/e0hm45Fi52TNOqwwZDLOBk6FAs/6URzq87dbudV5sJvDCBrhXb2Dn9DMoKkOWOGQDJ1xBk67Onrd2r1NjM7GXtpct0hZYcYWBPZnR988GTqwgvAVNurK3rjNjM7r3xp1b/behli5Y4i4DN+X6Nn0QOHGYln3/bODESj/p6tbILaN7Q8Vm7TF1sfn2oOZe2JAEfn/xSrNbnFjnmxoutcx7cb70cPq1OrCBE6igSVdllUb3ho7NfvemLjajdcUj1Ht/eI9eiwP7GcwF+G3M8TZc8tac80z8kTYYTr9WBzZwoqRfIWXPzr2W1lXE5gB4UxqbAwQeuHN4dyqXOIu4BH6bLvugCk+UyPcizfr+2cAJUdCkq2eeuzl8y6Wxmax7u//Orb7bB3c6tyXYUBK/jdne+gvNUDtX75EeTqe+fzZwQqRv1m9vaTezN3RsDnZvqmIzerjvNhQ8c2iXZuBHJ//S7BlnlQ6/zfmtVG/OfUu+KWnT6sAGjr/0k65OHD3h6thM7oXtm723ii8V04tycj+Dx4/fxhyfxG/9xeaGiy215z2P//Jxeglps8QhGzjO0jfrz50z1+2xmawLBRvVRdWuMDDhtzEbCSzx25zb2nTZW3CsWJ7QSo++fzZwnKWfdNXfPeD22Ky5t+/2zZ5brjCwHX7Bvc156OTjW07Sq5iQFn3/bOB4Sj/pKuub82kQm3GjF917s/d2ld/AE5zcz2CP36ZcLzwP9l6x5H35Qtze6sAGjpv0zfoffvBhVLEZHx0Vm9G6PWjjsZ5bUA43sC1+c7xI4PxWb2Gbt6jdk9P44jMv0gtxe6sDGzhu0k26mnpz+GaksdlPYGfFZixyb68LDGyHXwjPEr+NwswV31RP+vFEei2u7vtnA8dH+hVSKkorIorN2qMjYzOBFzw82n0TSr7GC7uyjeZJdQF+m/J81vjNDeAXq7C1Jb/1m53n5Mtxb98/GzgOCp50tSfC2Gwa9DosNgN7yb2wMXXiFHqZTuxnOA/VTPhtKVDhV5gZbNy+9r3P5Rvn0lYHNnCs0jfrz539CoTnNIvNaF3xCLV43mJnGliPX43A9viFbSAw/Ehzru/Fp7XB8AR3LnHIBo5V+klXbc3t6Rebb0oC99x6faFTW4J1+A0MgG3w2yII7LvS3nqloz6vedJPtMGwG/v+2cAxqbS0VLr3+OETaRmbybpj3bdGu24uXbCUXuyyWcuNFkpd2eEXZ1Mq8UtXhnMO58o30XV9/2zg6KWfdPXmsrfSNTajh7tuYvXc+uzDtfR6ndXPYIXflvy2kPhtLen0FePG1o+/lB52V6sDGzh66SZdTe3rHEjX2CzdO9J188AOxzUkAXLV+EXH2uPXW4Rwbi3pWPDyQulhF/X9s4GjlL5ZPz+nII1j82g3WncEN24e2K4tceYcA0v8Nugu/0r8NmtTO+zxW9zuLW5vKWy7nt3wxKNP0KtzUd8/GzgaBU26ev9DP3jTMDbDhnTvSOeYNLBDpkM3+0e/YF01fsGrCvy2lXW2lnaWnS2Xb6tb+v7ZwBELjs36SVdjg2PCtGkbm9G9XeheeCzPr3SUgSV+5ewr8KclfvHisD1+W/xnufZt0I5QE1zS6sAGjlj6SVeeak9EsfnemN+6LonN8AxYF2oMCNwxVpFf5RwDR4FfnOBhhd+2sq72q10AYaiVy1fJ99f5ff9s4Mikb9bfvX13JLEZrRsEXpfEZmIvuBfqan4FvfYf/sMPzI5KdpnwixZV4hcdK/DbWtphxq+vBL7U0VjYMv030+llOr/vnw0cgYImXc16JYLYbB70Ojs245P+2EzWHekaG+4YvV5WL49fRjslt1T4vQzu9arxS1814xee8V3puHKqTM7ucHirAxs4AuknXbU2tWVIbIYa7Ub3Qo12jTnEwAr84onoEPjtJPzCeNiAX3Bye0V3R0X317sDVxmc3OrABg5XQc36p7MyJzbDNlgXn+wYG24fdYKBCb8YlWPDryCwEb8+NDn6fNVbH8oX69i+fzZwWAqadPXGmxkVm9G9XWhdqBEdgY9tOGm2VpJK4Bd8q+E3T4XfgGOD8eu1x29nZXd7OdZLzwUGw85sdWADh6XAdaPJU/s6+zMtNmvuFQR+6rFptCtS1c9gwC8SWIlfJLASvxqBg/HbdrULquayZ/JPJ9HrdeZgmA0cWvpJVxUlFRkYm/Gf5OHO0aXztX6GVBnYgF9x4TdM/HZY4pf8jE4uD8IverisK+vARfnuO7DVgQ0cQpaTrjItNvvdOzbUNpJaAyvw25DdEgt+4dGA3/aKns7qXqid63bJz4DT+v7ZwCoFrZDy9HM06SqtY/NNy9iMT7aPgnvhURo4NdOhbfDbUtBG+AWjhsBvkQ1+r3YRfjsqeiR+scSXFs58TXrYUYNhNrBKW7cEVpf1VHsiiM3SvW6JzWLbLjaDdaFGOkaHWke2rt2aKgMLf4bALzhZjV/Iz2r8EoElfruu9XZW9TSXtsrBsKNaHdjAttJPujp+6Hj4sfneqN+6LonNOvDetIzNyN42dC887t2yL1UG1uMXCWyBXxwAK/AL22r8dlZq+G2v6Jb47ajsAQ8XntZWlpngpFYHNrC1DJOuwozN4tFlsTkw6JXINcVmsC66VxB4/zZtKvgLT75gNFgiS49f7fKvPX59xR0x4hdN68cvoriqBxx+aPMR6WGH9P2zga31jmzWnzzV19Ca9rFZ8tYyNkPJjRN7taVJkt3PYInfPDStGb/a7Cv/+SoFfuFLFvgtD+AXtsG94OTu2r6e2v53l/5RetgJff9sYAvpJ12d+/ocx2Zto31kyDd8NUdrmk2mgaPDr7cAb2qnwC+1ASvwiwT24xfsDdVc4pv2a+1KuBNaHdjARvX29gYmXb3+JsdmjcDwjG8YqiznavINbI3fAm30i/M3rPDr8zO2DQisxC/5U4XfGsQvFGxUZl+Tx/eUL3HIBjZKP+lqdGAs3Ngs3eu22By4YmQfm4dbkb243TYiCfz9h75ntFliCpypwC92Dub51PjFr8aAXyyBX9gAA0Od2f+N9HBq+/7ZwEHST7q6WlQeZmy+R7F5BB3rrtgcyM/K2AwFGzd8w1At1T65i8xmi3uBUSV+tcu/wfiF7Vjx6x/9tleICVhm/F7T8NtThxvo4Wu9H7/zsdwPKez7ZwMHpF8hZcPaDekdm/GrYcdm+BK5d8g3csM7nEwDS/zKy79R45ccq8Jvebcav13VmKV76/r6PANtVR0zfjeD9kMKWx3YwJr0k66ee/rZMQjPYcdmLS2nb2xG67ahdaHgmaQZ2BK/SGB7/NKTZvz6rmjsjQG/2hi4G59EJ1dkV0/5WYpbHdjAmoKa9RtbOTbrY/Ow373g6hstQ3JHJXo6tBm/8Iwav3inKyV+23GOZEz4JQL31Q/2egZyTgZWdUhJ3z8bGBU06ergsbSNzeLJ6GIzPQnuHWodfvmFl5NgYDV+wajW+C3Q8Av+tMQv3owyBvz21AoCA4dr+3qv94OHN63eJD88ye/7ZwMHNeu/MmsOx2bL2AzWFe4dGWwZWjJvSRIMrMYv+DkW/GIp8avNwbLHb1/DIGyAgcHbM/5bGwxPSHqrAxs4aNJVb1tvBLFZgtfxsVnaOOrYjOUdHhSPi+dqi4yue2e92XhxKTV+8VaSAr9QlvjFK0YCvwLFFvhtL+8i/GLvkRV+8YoR4Reoa4Vf2O5FJw/0N95outoy5eeTaZ8keYnDTDewYdJVBLFZbmRGbAbront9w4PNQ0te1QicuH6GMPELpcYv5Gc1fvGrMeAXSwTp4qzAOpXJ7PvPaAPrm/VXrVgVfmzG7QyLzVDganAv1J5NexJqYDN+G3PE5V97/IqvRoxfHAAL/CKBLfHrH/2KssBvX8PAQNMNqP6mG3s27ZUfp6T1/We0gfWTrkb6RtM1NiNpY47NuNEs/tl0Y++mxHYUGvCLBFbiFwkcG36h1PhFx9rjl6q/cXCxP5tMSFarQ+YaWL9CSlnh1XSNzZp1Y47NUPA8uBdq98bdtN+mPTrNbL8YS4FfNKcSv8hYJX6FY9X4RfdGil8kcOMgVHtNlxwMJ6fvP0MNrJ90tWvbrjSNzdr5Kl1sHos6Nt+Ax6Yb9NVzR7Jo1yWinyEkfmWWNuAXhsFq/LbJBmAb/HbV9MWC334RpGuK6+RHKwl9/5loYH2z/isz54z2j6VrbMYNGZsFhKOOzeheL3oYquRCWYIMTEa1xC8tYiT8iQRW4BejsiV+yzT8dlRY4xd7kgi/1Htki99+4WQjfvsbb4C9YePYrhPSw4nu+89EA+snXXnrfekam5HAweCNJTYPCRuLz+tQ4gzcKO4Iq8YvDYAV+MWmhRjwC9RV45fmYFnid9ALxzs85L23/D35GUtoq0PGGbi0NHC6/+j+Y2HFZjN4HR+bA2PdOMVm2gDIQF3xG/ih73zXbMKoy4BfJHAM+IVHa/z6R78dsgE4GL/CtIjf3usDlvjFAbDAb78VfmkXddZ1z/TPV0to339mGThohZSlyzk2hx+bB73D9NGEjfaaLnkQNPsw6tLjV7v8q8KvuPxrj982SeBynKFhxi95GNlbbY1fqOjwi3uyZai2qE6e0Epc339mGVhOunpk8tSe1t6QsfnOkLAux+YmZO9A8xAUbuj6Gcw+jK7s8AtOVuOX/GzAr0+HX2xaiAG/SGAr/A40a/gVj0H4FUdAdPL5ExfkjkpQq0MGGVjfrJ93MS+NYzOBN76xeTBoY1DuyZJj5WY3RlGW+G0CAivx6yvCmz8r8NsREr82o1+JX4CtAr99lJ9N+MW914w79tP318h9lYhWh0wxcNCkqz+vTOPY7PdwnGMzbWjMab4hd2Zc+hkas9GoCvxq94iNAb90ykqB364q/42vbPCLz0eCXzpu9jT0znxxJu2rRPT9Z4SBHzx4ICddQXge6R3h2BxFbKazNfDZhc+r7GeIi4HBvRp+hWnN+KWLw2hX2XsUCX6Ft1X4pZGwAr/9QGAlfgfpOBiMXzpKeqtbpz4cGAzHd3ZHRhhYP+mqrqKOY3N0sRnd2zKEYbJhcNGcRfEycPj4bckX7LXHLxJYiV/wpzV+tStGofHbJw5hCvwO+fc2vjtt+I5AFZwrlJ/A+A6G09/A+mb9XVt3pXFsxhlXiYzNgBr8sMJj/eDiVzQCv794pdmTEVUAvzhHkvDbYolfcLIav9TEr8Av3pUyBvz21fvnYNnh12vErzi2Ym37/Ev5OYxjq0OaG9gw6Src2GwCrwtic8C9iYrNUPA8fqbrB99e8jbt1Rj7GSzwm43uVeO3WVwcjhq/OAdLid9enIOlxq/2aBz9+k0rDqwafjETiZILO06IX99/mhtYP+mq5bo3LWMzfFCSE5vx81pPn9fB3Rv3xMXAZvyCP9X4BWfGiF80rRK/SGA1frGPn/A7HA5+6Z3tbemfOnEK7bd49f2ns4H1zfrfnDwbTmy+TQTm2GwVm6EGcAM/vjvX74zdwGr8Yo++Er++K+1q/LYhgVX4hYoCvxLCOH/DCr94SCX80rGVSrx9FflV8jMZl1aHtDWwftLV8qXLQ8dm8eiu2Ky7VpSM2IzPCPfCk5LAj07+pdmZYZaG33wVfpts8Es3tVPgF0uJ324icAz4hR2ixi+9R1qw6hKPHWMHth+UHo691SFtDay/btTl6+HYHHtsxg9uk7iU4hnIPnmZdm/U/QyE34ZLXjV+sZT41S7/2uMXe4+U+EUCq/FbHwK/A7RXQ+GXDtNQ77+zQno4xr7/9DSwftJVaX5Z+sZmDbzJic3wDLkXNuQtoKI2MOG3SdyMTsy+ssYvPE/4RXNa4ReK8IuOVeCXsrQJv0hgJX5FqfArKlz80mNvc/8zTzxNOzDGvv80NLB+0tXK91ama2wm8CYzNouhIH4VNorPl8RiYDN+6y82x4JfIrAFfv2jXySwEr9YavxS2eOXjqQq/Aonj4nPRkNVo/yUxtLqkG4G1q+Q8uxTzw73jKZlbNbACwROYmyG580EnhBVP4MRv/mtavySMwG/OB62xK8YAHeIi0aW+G2nK0bitLMlfruvicu/0eJXizwK/FJpz+Bn49Th03IfRr3EYboZeOuWLXKn1JZfT+fY7Hdv0mKz9iEmFNcPRG1gBX6h1PgVjlXhF/0cA37hn2r84h5T45cOvkr80hEfau1H6wIf16j6/tPKwPpJV0f3H0vX2IxPpiI2w0fZj+JBIFXUBm641GyJ36bLXjV+xUhYhV9wZgj82ox+JX7xdjmx4ZfeoJD4pQ9Pf9vg7N/Ppt0YXd9/+hg4aNLVy3MswcuxOZbYrHev3sAXdmWbXWpX2i3alfgFnMaIX3nKyg6/RGAFfqGiwC++a+It06KTAr9+CDdWNj7in90RRatD+hg40Kw/aWpznZh0pYzNAfC6JzYjgVMam5FIMEpswAWBJvtX1oyonwHwC/mZ8IvOtMIvzb5C/EpnRoJfoK4av93X+tT4pcOZAr9+Atvil95fFX6h+jDTQeWcDcw4irTVIU0MbJh0FTo2D3FsjiY2Q8HnmwC1YOaCSA0cEX7Bq2r8gkXV+O2s7o0RvwECW+KXZl8p8SudbIlfQQX8aO3YtEN+gCPq+08HA/f29uonXYURm4V1OTZHHpt7xWJ89OSiORG3BOvxSwX+FONhC/x6C/2XhcCcVvj1iU4GbfaVFX5p9pVG2sjxS4cwFX71t+Cwwi+lMDV+bw8IbAzcmTMjMBgOv9UhHQysn3Q11DWsiM004uXYHHVsFmgyEnjZrOVmr5rLDr/gXjV+6eJw1PjtEPd/VuAXCazEL+zkhOIXQh98/Dqbu2D0R7s0/FYH1xs4aNJVXqkiNt/ms80xx+Y+/wY8+fEfV9NuD7OfwRK/zbmtYeLXh962wq9/9It3n7TCb2eVhl8sK/yKTIEvTZDWAr+YU2LA72in2FDgd1CEwYE7JbkRL3HobgPrV0hZv2Z9WsZmCV4nxGaiE0053L4ugoYkisqx4LcVCKzEr/CwCr+dlf7W36jwi/lFjV8sC/xqR3zx6bLDL3wORXK8e2TfUfmRDqfv38UGDpp0Ne3Zoe6RELE5GLyuiM2AXEfFZunePuwo1JY4C8fAnvM4+wpt6W8/ssNvC/rZiF8cAMeIX7w/O+JXXgQ24FeOfvEisBV+4SAYAr/izQ0cyk341Zxsg1/6rEItX7JcejjkYNjFBtY36zfXtqRfbPaD10Gxmc739FxHZEkCh5wOLfELw2A1fr2F2KOvwC+6NEb84ktQ4lcQWIFfLfVEi9+bGiQs8Ivzjm7cG+4eeWSyNhgO2ergVgPrJ10d3nuEY3NyYjP8E1EmNorOXAnTwAH8ygFwng1+8/34LW63xC/6NqX4xQOrGr82o1+J35sCG3b4BQPfHbpXWx7uEoeuNLC+WX/OjDkcm5MWm8m9SK2avqIzxeEY2BK/DRdbYsEvlhK/HVU9avziEqGx4RfephD47QyBXy0JBuMX19AT7TffnDwrPazo+3elgfWTrrpauo3gTYPY7HNobAZe0elceKbotGbgH/7DD8y+DZQZv1ACv+htS/yiaTWLWuJXXDFC/IrrRhb4xSW8Nfz2WuIXCRwufofM+MVS47crFH4HNfzipzcYv3TfxXuj91etWCU9bNfq4D4DGyZdGd1riM16D7skNqNpnRqbpXvBABUXA7d3Mpo2PPw2o2NV+AX3qvGLd6WMAb894vCkwC9tK/CLx+7Y8CtGf0b8gofvjdwb6R197qlnaQ/btTq4zMD6Zv0P/rRSHZvvyNkaHJvjFJvxq2K5zd46fAxpYMKv4fKvH79eiV/4pwV+afaVPX6FSxG5/htQmvGrjX67dR1IQfj1YOjAg53gsBV+tdHvoB1+6VRWOPjttcSvcfQr8Xt/7P790Qe+htZf+E9oWfb9u8zAgUlXk6be6MRJV3ax+daA/yDnltjsP1/l5NhMn365oTYw4bcxx6fGL1Zs+NVOWdnjt4vu1W6PXzyKKfGrHUZt8IvHaDV+cUOJ3yEjfsXK1Q/ujz3Iu5Qv97O5799NBtavkFKSW2obm115ttkdsZlO3mqPNX3y7Ti24aTZwIRfvFVdmPjFu16p8OufLxmMX+HtWPCLJXagLpjYnHxuscYvvvux4vcu4RfYK/EL7r1/E7YfbFi7Qe5qQ6uDawysn3S1c/NOu9gc2HBLbKa07J7YLDb6yQ+P/8fj2jti6meIFL9IYCV+NQLb4xcJHBt+icAq/Ip3TYFfOrJHjV/Mz8H4vX/zwYOb344Njs2d/QrtakOrgzsMrG/Wn/PynBsdQ2kWmyV4nR+bpXvhq6/Nsl/izAa/4E9L/CKBBX5hlGuN3xINv+RPM37Bzxp+BXXt8Yu+jQa/+KjCLx7oQ+CXfAsfWsFhE37vigWrEb9jAfxCPbj1bWtj2y+mBAbDcnaHOwysn3TVdK1ZFZuDweuK2CwHva6IzQi0OnFVprp3wcvWLcG2+M3B8KzGL341BvziMFiJX9hQ4xcJHBt+R3vws6TCr7CuAr/Cxhp+wb3f3v6L55rnq2NfSRfIadIuMDCEfvl3H96Dk65Cx+Y+18Rmzbruic0BAtcCgV+j98U4HTpa/LaWaPiFR2v8+ke/HeXd1vgV0QBfCB1uzPi9HgK/ItREj1+xocIvFaTIa2W1UJezLh/dd/TofqwNn2148/XlEr/Xq67LT76h5NQOpxtYP+lq2eJl6Rmb/e51RWzWHq9hU97nH2gnV/QGBpY25flixC8yVolfJLASv0TgqPFLe16FXzpwt434alrL8yrL8yslftvqOw7tOrT9i+1vvPaGLNpREr81VwNzgfX15tLlEr+97X3HDx0/fvjE8cPHs05nTfzZz+W3yXkdTjewbtLVlM7mrjSLzfq07JbYDM+IacY9PTV9Oz6z6ig8DyXWSdHPvrLCLzyv9R4F49cbCr/tofGLByPtFtDW+BUEFht6/DaUNpdeKivNvgqV/03B/q37ofZt3S/xW3vl+tJ5S6c+PNnsPYnfysJqegatu+iNZVCLl+3YvENM2hcEHrhzo3MY8FtTVtsi7t9mMfq9qY1+ocpLKuQAeELwiWhHG1jfrJ999nKaxWZ5vspFsRk8QO6F7wfEbf9Mu5PTC0++YMavRmB7/MK2Gr/tYoZGLPjtwXW6Eb+tFe27N+zZBbV+9671e95d+sfFcxYvfmXx4rmLJX7LLl0125JK4vd6Wf3r85d+tuqz/dsOYH154KsDXwN+KwqqCL80+r3Zp41+8dOoHP3Skl3m0S8OgMXotyCnQP+XuOYyUtCkqz9+wLHZIbG5i5bzq0QPH9p8hN6gQD+DFX7Bpdb4pcu/V0TvkRV+tdlXYtEjcGz+V4V5J7FyTxTu33hQ4rcsq2LhzNdem7Vo0exF0349Tf9xl/gtPqv1TlE99X+fAutS6fG7d/M+wu/VnHIofAfF6FeMhHWj37YQo1/6yOHoV0xD0I9+ycO6k8/3NfySdYPxm58dcC+MJc0roTnUwA8ePNBNuppC1404NjshNkv3wmPB10V6A9vhF6dSWuH3+sX6i/svX9p/+eimE1tWbdv60bZtq79cuXzVgpcXSvxePpavN56+mktbCb9Xz1eCgalem/kaBPud63YJ0u4m/CKBKzt8le3G0W9DiNEvnV+Ed5DeccPoV4Yy+gip8ashJxL86tlrd6c7hxpYP+nqWlkNx2bnxGZ6EjeqegGJ9B79/Ic/zdpxMWvnxarTdYTfq6eqwJOfvbtu/ksL5r00f96L8+a9OJ+++eqpSsJv5bkasy2h5k9fUPx1KeG3oaB5+5od2z/bCXXh8CXCb+7xgmuX6rTRrwjPlBHwFVmNfvF5MfrtEwc43M+m0S/tajhiDtApK/HOhotfbcMevzc0/OIEJEv8mka/G9dtlDvEzr3jzjSwftIVDP1DxGY/eDk2xys21+XXF525Uni6GB4vHs3esXbnzs93A9Zqcz3kYSgIq5N/qt3YXV8X9l7GnCzwS8+8+vw8dO9L8+e/NH/tnz7f+tGXleeq5egX8HvpwOXCEyWicd9i9IvLL/hHvxCzzaPfTlp+wX70i+efxcln3ANK/A62hMAvHLJjxa+4ZaoCvw8EfvXu/a/f/tbOveMONHDQpKsZs9MvNmsETlZs9pW3QfX6Y/PujXvAjVC7N+x5d8m74MNFc3DoWJldLWOz2ZZU4GfNIdd6Iax++PbH61cG5ugCfrN2Xqo5d11/+bclv41Gv83a1A4c/cJX5ehXnnxuhQGw/ehXmBPnSBJ1Ayef9fj1n3ymssAv3nQS9yplHAv8isu/CvxS/qLPRgj89kaDX3KvfuZzyMVWHGfg4ElXTekUmyWBY4nNJRfLirNKoa6cLzu641hDSZOMzYvmLF4kzqwaTuRcOV8qY7P++ad+PQ0NPHvR4jmLyy9UydgMA8jis1cKThVBAYcNsZlYB66gjcCbJUa/YE7L0S86lmwJRrU6+Rw+ftsrQuAXHavEL/o2BvxqnxAFfunDqcAvgtcCv2MDY/om/pDuHXeagfXN+qePnUmn2KwNelv8aVkXm0sulEGdO5q1+4u9UHs371v93ifgw5rCWhmb9cbTV/G5Ehmb0b1z0cNQeMlkPWJ214Y9XiSwhqbWynbL2Eyfe+1RnG2WaVm/QVaBb9A8U63rKBQnnwP4xTtsqPCLjlXiF0yrxi8uyBADfrHU+G21wy8e8cm3IfA7YI1f4WEjfsG9r8zSOhYmhOfecUcZ2DDpyghek3udFpu91b6y7KtQXZ4eGZv3btq35dMtS15dArV03tLFc5fQCwTHythstiUUWLE675qMzXs27YP0C3X5dB4RGH5DfXGjZWyOw9nmWs20coNMS18l3Inb2QQIvGvNvvDx21LQqsYvlhK/dJSJBb+wb9X4FQRW4rcjNvwiezX8jvaPzdW5d+uWLeG4d9xRBtZfN+po7ExtbPZWt5bnVl69XAFVeL6IZuTs//JAS5VPxma7GTlll67K2EzPoIHnLQH3Qm3+bCu4sbGsRcZmSMWQh68V1NrF5iSfbdbSsn1sBvOQe+Gbpz833W/gvRK/YoIk4hdRrMQvGlWJX/xfYsWv2JkK/Ip3IWL8io+W2KCsZ8JvYPQrbhpjxu/IPcLv6IDRvUZv2MspBtZPurpyuSS+sbnfO9Db1C9j8/7tBw5sPwhuhMcVf1jx+oLXoZbOX1pX6pGx2WxLKvCzjM1L5y359P01+7cd2LtlH9TJfV+BdaG6gcChYrN2viqlZ5vp0689RhKbsaFPoA9ctGCG1pC0e80+I34vQ3gW7Qp44VesD2rCL301avzKOGCHX9xpSvyKIY8Svzi8UuF3FIkSPX699T5505xI3TvuEAMbJl2pYrMIzFXF1VVF1ZWF+Pj14VP1FY0yNr/x2huvL8R65omn9ca78PUlGZv12Hz68adfX4juhSo4XyRjM7ixPLeiHAicUw6mtYvNoc82y/NVCT7bnMzYDD+FeLza1YkdhQtpT67/04ao8eu9ggQ241f8p/g/IoGt8IvXewV+JYEN+MWcQvhtwGiTWPyKbBgRfvW3vJpgdceckHKEgeUKKY/+6y/3bd93ePdhqHUfrwM3Zn+To8Xmvtuzp8/SezLwsnce0vZmz61t6798XfPw6wd3HALGQsE3+GpaQ8Zmep/Iw/E52yxNG/nZ5tTGZnKsXWwm93ZU9YCX5vsJ/OoL85DAlvi1Gf1K/AoCq/ArCCzwi/36Fvil+BA1fqFC4FdfVviFz2cI/GodvwH8ej0+uQLDBNMk5zCVegPr8WuoZYveyD6dLc9XXTqdfWj3IXBj0aVigDARuO16h2Vsds7ZZs267onNMi3bxeYOWowXCFzV++UarZ9h3ovzm3KRsXb4DTjWHr/k56jxi2WJ30YNvxqBzfjFt9JPYCV+8XxKrPi9D+69WnRVz97o3DvuBAOP63oGqWb/flZ1cbUhNjvnbHMksVmfn90RmzWsKWMzuRdcBI6SBgYCq/GLBFbiVyOwAr82o98Afuv6QuAXU48KvxSposavHPrZ4xfzc+6FXP0HPmr3jjvEwOPiJJa8hkS17qN1A203DOer4n62OYGxWWzgAR5vzuKC2Nx9zXi+yi4208lhdF15tzTwU796ivDbUtAWCr+4PqgKvwLyCvxSTFDgFw9nUeAXjtECvxi4rPCr8VajrgV+0beEXySwFX6H7+vda9lgFJGcYuBxMYly65Yteg9PnTjl1OHTcIQLIFd5ttlpsRme1z5Ajo/N3aHONutjM9mJNk7u0C4fTPzRw4TfhuwWDb/UkR8hfnEOlhK/YiSswi/s3hD4Fe+jAr90fFfhtzsEfkX3qwV+De5VTHIOUw4yMAkOSPKCMNWs6bNgrBvwMMfmVMdmKNqA78k9obW8Tf7JRD1+m3FmpQq/sK3GL/0ZFvitChe/RGBb/OLsqxD4pQ9J1Pi9Q2vu+fG7/tPAJOe4uHfcgQYm5eTkGBL1indWtHnE+SqOzamOzRqBK3vAirnHNQNP+tFEPX7ByYRf7fyzCb9ebOJH/BKBzfj1X/6NEr/asc8ev5SYFPgNfHiixi+UH79696objCKSQw08bpOoD+485PDYPJgBsRkJXNFNl3ykgR/6++9a4le7/BstfrXjhT1+NQjb4zdw+Tda/BIkYsGv3r1hTnIOU841MMmcqJ954unKwmqOzXr3Jjk2t9FiYldxUc/6PK3vF8oKvx2EX2+Bdm3JgF85+m2/Ku4+acIvmlaJX9wPAr/Ctxb47RXHyqjxKzFgi19ZVvgd7hlZ+efIGowiktMNTLJI1G+vaK1p49icktgM1VHRQzbr0PUzKPDro1NW9vgVHlbhl/68qPErDqBK/CKBlfjVyh6/2uXfAH6HuobnvDwnce4dd4uBxy0T9cNTDuw4yLE5utjcXaO5t0O4JaLYDN+vnSIWK2vLd6T8TJUNfvGmdgr8tpWFwC++HCV+7Ua/Er/94o1T4Vd8JBT4RSdHgt+hnpFXdO4Nv8EoIrnGwCQY+htmfUCiLs+rHObYHHlsxrQcVWxG8EoC63r6967dp8ZvG/hfiV+8vbMav9dC4Bcdq8QvHXkjxe+YH794CVONX+HeG51B7I20RSF8uczApNLSUnOixtnOqYrNIjNnVGymjfZy8HPHvOna3er2fLaP8CvWRrHAL341BvxiKfGLI2ElfjFDxYZf0XKkxO/g3ebaFv0k58S5d9ylBh4XiVp/58oJmKgn7992oLuhN9mx2Q/ewUyKzeIRLQffsOD3Wj8DGDgc/PquaOy1w688iNjhVxzLYsIvvK1q/NK4TIVf0dlqxi+6d1JMDUYRya0GJpkT9dOPP51/rpDeA0HgBMdmMejtx89KZsVmci8QGMgpDbxq+Uf60S9Q1xK/5Nh28Z9a41ccXAKXf034pQNW1PjV3lkFfgV1FfhF3lrht7kmyL2xTHIOU+42MMmcqJfOX+qtbk1ObA6cr3JPbJbujTo2B4xX2vnW/D/Qbn/1xXkSv2jaglY1fvFuWDHgl/aMAr/+46ktfvFcSYz4DfQe3a0pq02ye8fTw8DjVokaav/W/d2enoTG5oGmDI3N1DAE3wCP21Zvpx0+f/oCiV+805USv0hgJX7tRr8SvxqBFfht1PAr+vWt8EunstrwFEkU+MWbxvjxm3Pusv6zlxz3jqeNgUkWifqxp/LPFnBsJvDiRKs4xWYo+B4i8NaPttHenocENuEXcK3ELy72q8QvHqGU+KUdGDV+xcFdiV/qn7HHb05WwL2xNxhFpLQyMKm2tlbe4oNq6bylLZU+js1xjM1+AneBD7d9/CXt5/985D8ixS+WEr8UMRT4xXdEiV/ITRp+m6zxO9wuCKzAb7eG37E+XEDPgN/ss0Hujdck5zCVhgYeF4laf5c8qn1b9nXX93Jsjktsxo2reEtnqG/2ZtEenvTjiXb4bcU2fRV+kcCx4RcOkSr8NuHbrcYvzR2ICL87Nmvt0Clx73i6Gpg0OjpqTtTnjp7n2Bx7bEZDCqiCG3MO59HunfyTSXQR2IxfyM/kWPwfrfBLxw7Er7jrVaT4xVLil5CrwC8SWIlf8+h33ep18qMVxwajiJTOBiaZE/WSV5fUXbnOsTmW2AwFP044zTmqGRgJnOuLBb90NFHgV5QKv5LAtvj1hcAv+TYkfj//5HP5iUrEJOcwlf4GHrdJ1JvXbOnydEccm/3uzfDYDD+FVgRnlnRIAkMRfukisBm/cvRLzDfjt0PcKwfL/5IN+MUdSL1HtPNN+KW3oF+0K0SBXySwGr/9t2+0D+nZm0L3jmeIgUnmRD3l55Ozjp0PPzb7weue2CzAm4jYDCUJDBtylxJ+6dsU+G0tQ8Ir8EuvK2r84ltmiV85+iXqmvHbpeEXb7pmhd/B9htzZsx2iHvHM8rAJHOiXjx3SU1RXfrF5nbB3gTFZvge2iAUy50ZX/zSizXjV45++3QdSPIYisfcJoxRKvyKCbaBy78m/Ipbvoh7oerwi+79fcC9n6z+JLXuHc9AA5PMN8Hc/NmW9prO9IjNEry4kZjYjBtXJIoDHYV5xwolfvFAoMQvnrJS4leYVoFfMftKiV+MVDHgFzf8+B1suzFb596EtiiErww18LhI1Pq1iCeIRH3uaJbrY7P/bLN0b4JisyRwS2HbpB9PpH24b91+iV/sGRT4xf9diV86+sQdv9oFfyKwNX6tR78B/NJyR/23m6qbnn3yGae5dzyTDUzyer3mc9RXzpe5PjZXJDw2i28Tnb3F7fOna/0M+z4/YMAv4VqBX4oPCvziEqFq/DaHwK+Y/a7Eb4cKv42VjY9MnOJA946zgUnmRP3JijVt1R2ujs2BE1eJic20ICgRWBp4z9p9Rvz6B8Ad5fjnWeBXvBZkb7U1fvGgRvi9rsIvLnokCBwxfjtV+G261vTIpIB7kzbJOUyxgTVZJuqjO44JrxoJ7PzYTOxNaGxG95Z0EIFffUm78eDbC9+2wy/+8hjwS3tMgV/xqMLvMBI4MvxWFVXr2es0946zgQ0yJ+qXfzejOKtUfFzcEZsleBMdmwMELmh7/42VtLvmvzRfjV/hbSv8+ke/hGIzfnGfE37xLVDhF2FrhV9ycvj4zf4mR/9JcKB7x9nAljIn6tV/+qS1qsP5sTnRZ5v1sVkjcAGuxrB5lXa/wYUzFqrxS3+/Ar+0TxT4pcgTNX7xBg9q/Iq6eOqSfPfhw1BbW2v8lDhDbGBrQaI23AQTE/X2YxybZWxGCIs7YMGgd8sqraNw/vT5hF/85Ur84p8aOX77kcBaFLLGr+w2o9ZfE34FgQVsqcz47bl16Uy23r0pmeQcptjAKplvKz/jv2cUny3h2EzgRTOLi0ab/ASe/NNJdIxQ4xePSrHht09MlVPh129aW/zSfYhN+P1yo3Z/Aue7d5wNHI7Mt5X/+I+rPUWNGR6bW4uRvTj5uajt0kGtJxYbknT4xb9KiV80bSLw6w2F3y4Nv4FHgd+1HwUmOTvfveNs4DBlvq385J9N2rV+dybHZih4HldjyG+9dMhvYEHgMPGLCUKJ314KMvb49UPYFr94V0MlfqEkfvXuTfkk5zDFBo5A5kQ97dfTir65kpmxGb6H3Au/59IBzcD/8o//LPErR78iC6jwC3HDEr8UcBC/9QPW+BW30ekXTg6F31EFfntbBtzo3nE2cBQyJ+p3l7xbX9yYabEZp0wWiiXL8lqLviqRe8OM3zYksAq/FDeixq+Y+qrEb5sKv/2+wdm/n+VG946zgaOTRaL+6aSd63ZlVGxGAgv3+oram3N9cldEjl8xAKYwYolfOfrFmVgq/OJt66zwSwNgNG3HqAG/vc39s6YH3OuEBqOIxAaOXpCoDQ3G0379ZPHZEvoIJjQ2dzggNoN10dhi5W74ktwJdvgl0yrwS8MQFX7rQ+B3QNwgKXz89nsHZuvc66hJzmGKDRyrzLeVf2fxu9cLG+hDmZDY7HdvamNza3EHLiaa6wPDN+kIfGbPORV+hXujwC+NhBX4FaVR1xq/waNfT1nDM0887Wr3jrOB4yLzbeUhUe9av7u5xBf32IyAdUxsRvcWteF9sPJbH/v3x+i17/v8gAK/eJxS4hdLjV8qe/ziAjpq/IJ7yxumOrXBKCKxgeMm823ln/zVkxePZqdxbAbrQoGZmy775r2orVF44IvDgN8OuheXAr+iN9CMXzqJIGa84UzV+OMX3Hu1YerDjm5RCF9s4DjLnKgXzV5Um+uJV2wWOdkpsRnKW4juhZr7/Fx6vUhgigAx4BevzCnxiwRW4hdga4nfioIqPXtd7d5xNnAiZE7UUDvW7mwq9sYSm9sxJDsrNiOEL/ta8vBREnjhzIUSv1rUN+PXP/qFSKLGL07SiAq/tCCWAb8Xvg60KKSBe8fZwImTOVFP+/WT5w9dijU2+zecEJuhvP6NT/6whl7mghkLJX6JwAr8EoERs0RgE357xITzqPGrrfAunrnwlTsajCISGzixgmO8ocF44czXanKuRxmbBdCcE5u9hW1IYPS8b/MH/o7Cma9p+BV/Nv39SOAY8EvN2Gb8+gksLv9a4Rdjs8CvoT3Q+ZOcwxQbOOGyvK389jU7GgtbIonN+g2nxGYogWIv1Bfvb6KXNv256Qb8wssMgV+b0a/Eb4DAlvil2Vf2+N3/ZWBEk07uHWcDJ03m28o/+Z9Pntx5ytWxWRAY3dtS0Lp7zV56XZN/OskWv9Uh8ItTOJT4hWcixe9nH65NV/eOs4GTLPNt5Re8vLD0m3KXxmawbnOerzHHC3XhgHYDGjCwJX5xyKDELxJYid/B5qFI8fvpys/krnbXJOcwxQZOtiwT9ecrNjQWee1iM4HXgbEZfiG5F37hhb0BA1viF5cdFPjFBkxL/OIVozDxq7X+2uJXOFnP3rR07zgbOFUyJ+pJP5l4YvvX5tgs5kI4NDaDdYHABOEL+zQDf/9//W/CLxLYCr80syVq/A7g8jdK/LaN9DT0zXxxZtq7d5wNnFpZJOoZC0vPllNsbseJVo6Ozd4AgVvLvqqQryIc/Mo5WHb4xTlYVvgdwFV/Eb+Qky3x29PQO0vnXtc1GEUkNnCKRYnaMHlr3Z/XNxV7nR+btY381sbsluZcr/z7FaPfAH7xipESv7rLv5b4pcXZDfg1uNe9k5zDFBvYETLfVh4S9ckdXzs8NsNPgXWh4PmG7Bb5x+vxi2uIKvGLBI4cv0M+GgCLkbAfv3Ulnoxy7zgb2FEy31Z+we8X5J0sdGxsJvfCLwT36g18aMthwm+HmDeqwC+u0B0bfodaBYHbR+tKPVMfnpxR7h1nAztQ5kS98s0P6/OanBmbm3K9YF34noZLLS9Me4H+4P1fHI4Yvx68XX7U+K29cl3v3pycHONuTVOxgZ0oy0R9YOMhSVppWiJwCmNzAxH4UgvUnP9+hf7aQ1uPRITfHnGDXgV+adsOv9dL6/XuTYMWhfDFBnauzIn6pWdfunws31GxGX4hWBc43JTjffV57Zadmz/aaolfmoOlwC+WEr+DYoVBPX6zjp3X76KMcu84G9j5MifqVW996MlpdEhshoJvaLjYAiUJ/Nqs1yzx20X3arfHr7iNjgq/g8H4vXDygtwtsJcyzb3jbGBXyLxQEyTqfesPOCE2I4GFe+E7v1ixmf68hS8vjAW/RGAzfgOjX3wczjoaYG/6TXIOU2xg18h8W/mXnpuefSg3ZbH5MsZmsG6T+Lb6i80b/6w1JC0EAseAXyIw4hfLCr/e4f3b9rN7x9nArpP5tvIfLFt1/VJDsmNzvgbepjwfWBcKfv8Xf9YI/OxjzyB+xSkre/z2gXvV+IVtS/x++v6n7F4SG9h9Mt9WHhL1llXbUhKbyb3wbfUXmg9+foT+nsk/m0R3LFDgFzbU+BUEtsCv3r0QSWB8YdxBmSQ2sFtlTtRPPPr4pYOXkxOb6dvAuvBrwbpQsHF+l3bXiyk/myzx21OLC0cp8SsIbI/fAZGZCb+ffhDk3jSe5Bym2MDuljlRvzn3rfIz1QmNzRK8zX734jMXmqWBkcB+/BKBEb+CupHiF4bBhN/O2q6ZL7zM7jWIDex6WSTqHz+8ZeXWBMVmRK4uNovv9yKBLwcI/NDff9eAX3xU4hc2FPjt9vTMfCEwyfmdt99h95LYwGki80JNj//y8Yv7L8c3NpN19bFZI7DYqD5TJ/93A357cJ1uxC8S2BK/Dbb4Bfa+/HyAvRkyyTlMsYHTSubbyi9/5c2KM9WJi80EYXpsuBToKLTDL06ljAS/tcXX9exl9xrEBk43mW8rP/FHD2/+YGuCYrPm4cs+z/lmKPmf1onFKOzwC9QNgV+cvzF0Lb92ys8zrsEoIrGB01Pm28o//u+Pndl5Nu6xmZ4h9zZcChj4yJfHFPjto9s72+CXZl/VFNVNycgGo4jEBk5nmRP1qy/Mqzh7Lb6xGawLv81zvglqzn/5G5I2Hw4Lvw0W+B1suVFTWKdnbwZOcg5TbOA0lzlRQ21ZubXm3PVIY7PGW1NsbsINdC8cAub+7lX6L45sO4onopX4xTZ9K/yeO5ql/2vZvQqxgTNCNon6XFxiM1gXzX+xxZPVJA38yR/XoGMFfpG0VvhF3xJ+mwL41bs3MxuMIhIbOINkTtTzXpxXerLCMjYH3CuRaxObcSOrieqNWcvoNy+avShM/GL58Xv2SJB7M3mSc5hiA2eWLG8rv2XVtmvfXJexuTFHO8kcZmyW7oXa+J7WkAQGVo9+JX77mwYJv3s372P3Rio2cCbKfFt5SNRfbz2D56vyWiONzXYGNuCXrhjZ4Xf1e4FbCLF7wxcbOHNlvq38vBfn5x0sjDQ2Wxr41//2Kz1+8RY59vhds0JbW3gCNxhFKDZwRssyUX/y5qfVZ+rCj836+mrTafolU3422Qa/eMpKj189e7lFIVKxgVkWiXrSjx8+9PnRMGOzvrJ2XKTfMPXnk0Pit722Uz/Jmd0bhdjALE3mRP3q8/NyDxSGjM2WBtYIbINfqLZrnTN+N0P+X9xgFJ3YwKyALBP16jfXVJ2uVcRmSwNDIYHrxQ1iTfgF9778fMC9PMk5arGBWUaZbys/8UcPH1x7xGxXy5I/hTOx8DY6Rvw2ljXr2cvujUVsYJa1zLeVn/u7V3P3F5gda2dgiV94lPitzqvhBqM4ig3MUsl8W/n3Fq6oOlVr9q3ZwFeySgm/RGDA77X8IPdyg1HsYgOzQsgiUf/Lwzs+3G22LhV8lb7tyI7jevxW5V7jBqO4iw3MCkvmRP38E89n7bhoNnCgIWn7MYnfc0ey2L2JEBuYFYHCSdR6AxN+zxw4K7+fG4ziKzYwKzKFTNTSwH9a9ifA79nD5/Tu5UnO8RUbmBWNzLeVl4kamEzPLH5lMUCY3ZtQsYFZ0ct8W3lw7+rlWmfCD//PD9i9iRYbmBWTzLeVNxewmt2bILGBWXGQOVHr3cuTnBMnNjArbjInanZvosUGZsVTYNeNGzaQe3/z3HPs3kSLDcyKv+rr69taW43PshIgNjCL5WKxgVksF+v/A52MBDQVY6rSAAAAAElFTkSuQmCC";
//		byte[] decode = Base64.getDecoder().decode(s);
//		String directoryPath = "C:\\Users\\jsol7\\Downloads\\open\\open\\abab.jpg";
//
//		Path path = Paths.get(directoryPath);
//		Files.write(path, decode);


	}
	@PostConstruct
	private static void makeXmlFile() {
		String directoryPath = "C:\\Users\\jsol7\\Downloads\\open\\open\\";
		File[] files = getFiles(directoryPath);

		if (files != null) {
			for (File file : files) {
				try (FileReader rw = new FileReader(file)) {
					System.out.println("Reading file: " + file.getName());

					StringBuilder content = new StringBuilder();
					int i;
					while ((i = rw.read()) != -1) {
						content.append((char) i);
					}

					parseXml(content.toString(),file);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			System.out.println("No .hml files found in the directory.");
		}
	}

	public static File[] getFiles(String directoryPath) {
		File directory = new File(directoryPath);

		// .hml 파일을 필터링하여 목록 가져오기
		File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".hml"));
		return files;
	}
}
