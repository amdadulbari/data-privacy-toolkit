"""
/*******************************************************************
 * IBM Confidential                                                *
 *                                                                 *
 * Copyright IBM Corp. 2020                                        *
 *                                                                 *
 * The source code for this program is not published or otherwise  *
 * divested of its trade secrets, irrespective of what has         *
 * been deposited with the U.S. Copyright Office.                  *
 *******************************************************************/
"""

def test_validate(docker_run, compare_csv_total_rows_and_cols, ):
    """
    The test.
    """
    docker_run(__file__)

    compare_csv_total_rows_and_cols(__file__)