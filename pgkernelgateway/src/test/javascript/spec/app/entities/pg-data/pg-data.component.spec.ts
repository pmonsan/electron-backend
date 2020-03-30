/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgDataComponent } from 'app/entities/pg-data/pg-data.component';
import { PgDataService } from 'app/entities/pg-data/pg-data.service';
import { PgData } from 'app/shared/model/pg-data.model';

describe('Component Tests', () => {
  describe('PgData Management Component', () => {
    let comp: PgDataComponent;
    let fixture: ComponentFixture<PgDataComponent>;
    let service: PgDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgDataComponent],
        providers: []
      })
        .overrideTemplate(PgDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgData(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
