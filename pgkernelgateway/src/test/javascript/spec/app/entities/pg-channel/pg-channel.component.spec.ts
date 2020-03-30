/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelComponent } from 'app/entities/pg-channel/pg-channel.component';
import { PgChannelService } from 'app/entities/pg-channel/pg-channel.service';
import { PgChannel } from 'app/shared/model/pg-channel.model';

describe('Component Tests', () => {
  describe('PgChannel Management Component', () => {
    let comp: PgChannelComponent;
    let fixture: ComponentFixture<PgChannelComponent>;
    let service: PgChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelComponent],
        providers: []
      })
        .overrideTemplate(PgChannelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgChannelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgChannelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgChannel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgChannels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
